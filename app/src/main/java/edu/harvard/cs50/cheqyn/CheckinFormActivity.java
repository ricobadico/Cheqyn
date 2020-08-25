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
import android.widget.LinearLayout;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
                    int checkinID = (int) MainActivity.database.threadDao().insertCheckin(newCheckin);

                    // Lastly, create field entries with the new checkin ID added.
                    // First, we need an arbitrary set of all the custom fieldds (we can use the ones at ID=1, the initial checkin, since it will have been created already for certain)
                    List<CheckinFields> setOfFields = MainActivity.database.threadDao().getFields(1);
                    addNewFieldData(setOfFields, threadId, checkinID);

                    finish();

                } catch (ParseException e) {
                    e.printStackTrace();
                    Log.e("edebug", "Error reading date data");
                }
            }
        });
    }
    // Creates a new instance of each field with the new checkin's checkinID (for data storage)
    public void addNewFieldData(List<CheckinFields> fieldParams, int threadID, int checkinID){
        for (int i = 0; i < fieldParams.size(); i++) {
                MainActivity.database.threadDao().createFieldData(threadID, checkinID, fieldParams.get(i).fieldTitle);
        }
    }
}