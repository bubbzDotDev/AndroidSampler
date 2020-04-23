package macbeth.androidsampler.CalendarDisplay;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import macbeth.androidsampler.R;

/**
 * This activity will read the google calendar entries for the user that is logged in and
 * display them.
 */
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

        // Create array lists ready to store the list of calendar names and id's from the
        // the user after login.  The names will be a "human-readable" name and the id
        // will contain the information needed to read calendar entries.  It is assumed
        // that the name and id will be the same index in both array lists.
        calendarNames = new ArrayList<String>();
        calendarIds = new ArrayList<String>();
        calendarView.setMovementMethod(new ScrollingMovementMethod());

        loginGoogleCalendar();
    }

    private void loginGoogleCalendar() {
        // Check to see we are already logged in.  Null will be returned if not logged in yet.
        // It is assumed that the user will login via the GoogleLogin activity.
        // https://developers.google.com/identity/sign-in/android/sign-in
        // https://developers.google.com/android/reference/com/google/android/gms/auth/api/signin/GoogleSignIn#getLastSignedInAccount(android.content.Context)
        account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null) {
            // Using the google account, gain access to teh Google Calendar
            // https://developers.google.com/calendar/quickstart/java
            GoogleAccountCredential credential = GoogleAccountCredential.usingOAuth2(this, SCOPES);
            credential.setSelectedAccount(account.getAccount());
            service = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential).build();

            // Run a background task to get Calendar data
            new CalendarListTask().execute();
        }

        else {
            service = null;
        }
    }

    /**
     * Populate the spinner with calendar names.
     */
    private void createSpinnerAdapter() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, calendarNames);
        spinner.setAdapter(adapter);
    }

    private class CalendarListTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {

            try {
                // Get a list of calendar names and id's using the Calendar API
                // https://developers.google.com/resources/api-libraries/documentation/calendar/v3/java/latest/com/google/api/services/calendar/Calendar.html
                CalendarList calendarList = service.calendarList().list().setPageToken(null).execute();
                List<CalendarListEntry> calendars = calendarList.getItems();
                for (CalendarListEntry calendarListEntry : calendars) {
                    calendarNames.add(calendarListEntry.getSummary());
                    calendarIds.add(calendarListEntry.getId());
                }
            }
            catch (UserRecoverableAuthIOException e) {
                startActivityForResult(e.getIntent(), 0);
            }
            catch (IOException ioe) {
                System.out.println(ioe.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            // When done getting all calendar names, update spinner
            createSpinnerAdapter();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Try to get calendar data again
        if (requestCode == 0) {
            new CalendarListTask().execute();
        }
    }

    /**
     * Display a dialog box to select a date.
     * https://developer.android.com/guide/topics/ui/controls/pickers
     * @param view
     */
    public void selectDate(View view) {
        DatePickerDialog dialog = new DatePickerDialog(this);
        dialog.setOnDateSetListener(
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        // Set the selectedDate based on what was selected by the user
                        selectedDate.setText((month+1) + "\\" + day + "\\" + year);

                        // Display all events in the Google Calendar based on the date
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
        // Show the user name
        calendarView.setText("Logged into Google as: "+account.getDisplayName());

        // To query the Calendar, need to convert the selected date to a start and end date
        // using the DataTime class.  Need to take into account the timezone.
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

        // Start a background task to read calendar entries
        new CalendarEventTask().execute(new DateTime(startDate), new DateTime(endDate));
    }

    private class CalendarEventTask extends AsyncTask<DateTime, Void, List<Event>> {


        @Override
        protected List<Event> doInBackground(DateTime... dates) {
            try {
                // Get the current selected calendar
                String calendarId = calendarIds.get((int)spinner.getSelectedItemId());
                Calendar.Events calEvents = service.events();
                Calendar.Events.List calEventsList = calEvents.list(calendarId);
                Log.d("CALDATE", dates[0].toStringRfc3339());
                Log.d("CALDATE", dates[1].toStringRfc3339());

                // Get all events sorted
                Events events = calEventsList
                                .setTimeMin(dates[0])
                                .setTimeMax(dates[1])
                                .setOrderBy("startTime")
                                .setSingleEvents(true)
                                .execute();  // Returns null if not logged into Google
                List<Event> eventList = events.getItems();
                return eventList;
            } catch (UserRecoverableAuthIOException e) {
                // Even if you are logged in, you have to have permissions granted to see
                // calendar data.  The activity below is built into the library.
                startActivityForResult(e.getIntent(), 0);
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
                // Display all the events in the result
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
