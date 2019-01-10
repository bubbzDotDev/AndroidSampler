package macbeth.androidsampler.CalendarDisplay;

import android.app.DatePickerDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.CalendarList;
import com.google.api.services.calendar.model.CalendarListEntry;
import com.google.api.services.calendar.model.Events;
import com.google.api.services.calendar.model.Event;


import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import macbeth.androidsampler.R;

public class CalendarDisplayActivity extends AppCompatActivity {

    private TextView selectedDate;
    private TextView calendarView;
    private Spinner spinner;

    private static HttpTransport HTTP_TRANSPORT = AndroidHttp.newCompatibleTransport();
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final List<String> SCOPES = Collections.singletonList(CalendarScopes.CALENDAR);

    private Calendar service;
    private GoogleSignInAccount account;
    private List<String> calendarNames;
    private List<String> calendarIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_display);
        setTitle("Calendar Display");
        selectedDate = findViewById(R.id.textView26);
        calendarView = findViewById(R.id.textView27);
        spinner = findViewById(R.id.spinner2);
        calendarNames = new ArrayList<String>();
        calendarIds = new ArrayList<String>();
        calendarView.setMovementMethod(new ScrollingMovementMethod());
        loginGoogleCalendar();
    }

    private void loginGoogleCalendar() {
       account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null) {
            GoogleAccountCredential credential = GoogleAccountCredential.usingOAuth2(this, SCOPES);
            credential.setSelectedAccount(account.getAccount());
            service = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential).build();
            new CalendarListTask().execute();
        }

        else {
            service = null;
        }
    }

    private void createSpinnerAdapter() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, calendarNames);
        spinner.setAdapter(adapter);
    }

    private class CalendarListTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {

            try {
                CalendarList calendarList = service.calendarList().list().setPageToken(null).execute();
                List<CalendarListEntry> calendars = calendarList.getItems();
                for (CalendarListEntry calendarListEntry : calendars) {
                    calendarNames.add(calendarListEntry.getSummary());
                    calendarIds.add(calendarListEntry.getId());
                }
            }
            catch (IOException ioe) {
                System.out.println(ioe.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            createSpinnerAdapter();
        }
    }

    public void selectDate(View view) {
        DatePickerDialog dialog = new DatePickerDialog(this);
        dialog.setOnDateSetListener(
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        selectedDate.setText((month+1) + "\\" + day + "\\" + year);
                        displayEvents(month, day, year);
                    }
                });
        dialog.show();
    }

    private void displayEvents(int month, int day, int year) {
        if (service == null) {
            calendarView.setText("Not Logged In To Google");
            return;
        }
        calendarView.setText("Logged into Google as: "+account.getDisplayName());
        String pattern = "M\\dd\\yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        // https://stackoverflow.com/questions/7672597/how-to-get-timezone-from-android-mobile
        simpleDateFormat.setTimeZone(TimeZone.getDefault());
        Date startDate = null;
        Date endDate = null;
        try {
            startDate = simpleDateFormat.parse(selectedDate.getText().toString());
            java.util.Calendar c = java.util.Calendar.getInstance();
            c.setTime(startDate);
            // https://stackoverflow.com/questions/428918/how-can-i-increment-a-date-by-one-day-in-java
            c.add(java.util.Calendar.DATE, 1);
            endDate = c.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        new CalendarEventTask().execute(new DateTime(startDate), new DateTime(endDate));
    }

    private class CalendarEventTask extends AsyncTask<DateTime, Void, List<Event>> {


        @Override
        protected List<Event> doInBackground(DateTime... dates) {
            try {
                String calendarId = calendarIds.get((int)spinner.getSelectedItemId());
                Calendar.Events calEvents = service.events();
                Calendar.Events.List calEventsList = calEvents.list(calendarId);
                Log.d("CALDATE", dates[0].toStringRfc3339());
                Log.d("CALDATE", dates[1].toStringRfc3339());
                Events events = calEventsList
                                .setTimeMin(dates[0])
                                .setTimeMax(dates[1])
                                .setOrderBy("startTime")
                                .setSingleEvents(true)
                                .execute();  // Returns null if not logged into Google
                List<Event> eventList = events.getItems();
                return eventList;
            } catch (UserRecoverableAuthIOException e) {
                startActivityForResult(e.getIntent(), 0); // Request permission to get access to Google Calendar
                return null;
            } catch (IOException ioe) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Event> events) {
            super.onPostExecute(events);
            if (events == null) {
                calendarView.append("\nUnable to access Calendar");
            }
            else if (events.isEmpty()) {
                calendarView.append("\nNo events in Calendar for this day.");
            } else {
                calendarView.append("\nEvents for selected date:");
                for ( Event event : events) {
                    DateTime start = event.getStart().getDateTime();
                    if (start == null) {
                        start = event.getStart().getDate();
                    }
                    calendarView.append("\n"+event.getSummary()+" - "+start);
                }
            }
        }


    }
}
