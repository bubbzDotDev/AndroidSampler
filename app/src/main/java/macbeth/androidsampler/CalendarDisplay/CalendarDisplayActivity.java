package macbeth.androidsampler.CalendarDisplay;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.icu.util.Calendar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import macbeth.androidsampler.R;

public class CalendarDisplayActivity extends AppCompatActivity {

    TextView selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_display);
        setTitle("Calendar Display");
        selectedDate = findViewById(R.id.textView26);
    }

    public void selectDate(View view) {
        DatePickerDialog dialog = new DatePickerDialog(this);
        dialog.setOnDateSetListener(
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        selectedDate.setText(month + "\\" + day + "\\" + year);
                    }
                });
        dialog.show();
    }
}
