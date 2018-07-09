package macbeth.androidsampler.CalendarDisplay;

import android.app.DatePickerDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.DatePicker;
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
import com.google.api.services.calendar.model.Events;
import com.google.api.services.calendar.model.Event;


import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import macbeth.androidsampler.R;

public class CalendarDisplayActivity extends AppCompatActivity {

    private TextView selectedDate;
    private TextView calendarView;

    private static HttpTransport HTTP_TRANSPORT = AndroidHttp.newCompatibleTransport();
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final List<String> SCOPES = Collections.singletonList(CalendarScopes.CALENDAR);

    private Calendar service;

    GoogleSignInAccount account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_display);
        setTitle("Calendar Display");
        selectedDate = findViewById(R.id.textView26);
        calendarView = findViewById(R.id.textView27);
        calendarView.setMovementMethod(new ScrollingMovementMethod());
        loginGoogleCalendar();
    }

    private void loginGoogleCalendar() {
       account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null) {
            GoogleAccountCredential credential = GoogleAccountCredential.usingOAuth2(this, SCOPES);
            credential.setSelectedAccount(account.getAccount());
            service = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential).build();

            // TODO: Want to create an AsyncTask to get Calendar ID's so I can select which calendar to display
/*            String pageToken = null;
            try {
                do {
                    CalendarList calendarList = service.calendarList().list().setPageToken(pageToken).execute();
                    List<CalendarListEntry> items = calendarList.getItems();

                    for (CalendarListEntry calendarListEntry : items) {
                        System.out.println(calendarListEntry.getSummary());
                    }
                    pageToken = calendarList.getNextPageToken();
                } while (pageToken != null);
            }
            catch (IOException ioe) {
            }*/
        }

        else {
            service = null;
        }
    }

    public void selectDate(View view) {
        DatePickerDialog dialog = new DatePickerDialog(this);
        dialog.setOnDateSetListener(
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        selectedDate.setText(month + "\\" + day + "\\" + year);
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
        DateTime todayStart = new DateTime(new Date(year, month, day));
        DateTime todayStop = new DateTime(new Date(year, month, day));
        new CalendarEventTask().execute(todayStart, todayStop);
    }

    private class CalendarEventTask extends AsyncTask<DateTime, Void, List<Event>> {
        @Override
        protected void onPostExecute(List<Event> events) {
            super.onPostExecute(events);
            if (events == null) {
                calendarView.append("\nUnable to access Calendar");
            }
            else if (events.isEmpty()) {
                calendarView.append("\nNo events in Calendar for this day.");
            } else {
                calendarView.append("\nEvents for today:");
                for ( Event event : events) {
                    DateTime start = event.getStart().getDateTime();
                    if (start == null) {
                        start = event.getStart().getDate();
                    }
                    calendarView.append("\n"+event.getSummary()+" - "+start);
                }
            }
        }

        @Override
        protected List<Event> doInBackground(DateTime... dates) {
            try {
                Calendar.Events calEvents = service.events();
                Calendar.Events.List calEventsList = calEvents.list("6540r99eidi8k33oghs8fjhkj4@group.calendar.google.com"); // My personal google calendar ID for "Macbeth Family"
                Events events = calEventsList.setMaxResults(10)
                                .setTimeMin(new DateTime(System.currentTimeMillis()))
                                //.setTimeMax(dates[1])   // TODO: I want to display just the calendar entries for the selected date (ie. between dates[0] and dates[1]
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


    }
}
