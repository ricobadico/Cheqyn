package edu.harvard.cs50.cheqyn;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CheckinFormActivity extends AppCompatActivity {
    private EditText titleField;
    private EditText dateField;
    private EditText timeField;
    DatePickerDialog picker;
    private Button submitButton;
    private int threadId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkin_form);

        // Code for user entering date:
        dateField = findViewById(R.id.editText_q2);
        dateField.setInputType(InputType.TYPE_NULL);
        dateField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                picker = new DatePickerDialog(CheckinFormActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                dateField.setText((monthOfYear+1) + "/" + dayOfMonth + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });

        // Entering the time
        timeField = findViewById(R.id.editText_q3);
        timeField.setInputType(InputType.TYPE_NULL);
        timeField.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Get Current Time
                final Calendar c = Calendar.getInstance();
                int mHour = c.get(Calendar.HOUR_OF_DAY);
                int mMinute = c.get(Calendar.MINUTE);
                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(CheckinFormActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                timeField.setText(hourOfDay + ":" + minute);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        });

        submitButton = findViewById(R.id.form_button1);
        titleField = findViewById(R.id.editText_q1);
        dateField = findViewById(R.id.editText_q2);
        timeField = findViewById(R.id.editText_q3);
        threadId = getIntent().getIntExtra("threadId", 0);


        // Submitting everything present on page to database
        submitButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                // Must convert to a single datetime
                DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm");
                try {
                    String checkinDate = String.valueOf(dateField.getText());
                    String checkinTime = String.valueOf(timeField.getText());
                    Date checkinDT = df.parse(checkinDate + " " + checkinTime);
                    assert checkinDT != null;

                    // Now update database with check in
                    //todo: update thread soonest date
                    CheckIn newCheckin = new CheckIn(threadId, checkinDT, titleField.getText().toString());
                    MainActivity.database.threadDao().insertCheckin(newCheckin);

                    // create fields entries for this particular checkin (this is done so that each checkin can save entered field data)
                    //todo... how do i do this?? (not sure how I could get all the various fields here). First thought is to:
                        //todo have only one row per thread field
                        //todo and add a new column to that field corresponding to each checkin as they are made (or as text is filled out in the fillout form). name it the checkinID so its finadable
                        // fill the editfields with the contents of those columns
                    //
                    //todo or, up creating the thread, make a basic version of the table for all the thread to use with special checkinid. Upon creation of a new checkin (also then), copy the data from the set of that special checkinid, with the updated checkinid

                    finish();

                } catch (ParseException e) {
                    e.printStackTrace();
                    Log.e("edebug", "Error reading date data");
                }
            }
        });

    }
}