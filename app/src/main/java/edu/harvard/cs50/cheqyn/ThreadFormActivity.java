package edu.harvard.cs50.cheqyn;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class ThreadFormActivity extends AppCompatActivity {
    private EditText titleField;
    private EditText dateField;
    private EditText timeField;
    DatePickerDialog picker;
    private Button submitButton;
    private Button newFieldButton;
    private LinearLayout layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_form);

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
                picker = new DatePickerDialog(ThreadFormActivity.this,
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
                TimePickerDialog timePickerDialog = new TimePickerDialog(ThreadFormActivity.this,
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

        // Code for adding (and tracking) additional things in the check ins
        newFieldButton = findViewById(R.id.add_button);
        newFieldButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                LinearLayout myLayout = findViewById(R.id.form_extra_fields);

                EditText addEditTitle = new EditText(ThreadFormActivity.this);
                RelativeLayout.LayoutParams params = (new RelativeLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT));
                params.setMargins(10,10,10,10);
                addEditTitle.setLayoutParams(params);
                addEditTitle.setHint("Add description of what you are tracking:");
                addEditTitle.setBackgroundColor(Color.parseColor("#CCCCCC"));
                addEditTitle.setEms(15);
                addEditTitle.setPadding(2,0,2,0);


                myLayout.addView(addEditTitle);
            }
        });


        submitButton = findViewById(R.id.form_button1);
        titleField = findViewById(R.id.editText_q1);
        dateField = findViewById(R.id.editText_q2);
        timeField = findViewById(R.id.editText_q3);


        // Submitting everything present on page to database
        // Todo add extra field info (maybe by a for loop, adding columns to same id once created)
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

                    // Now update database with text fields //todo may have to check this
                    CheckInThread thread = new CheckInThread(String.valueOf(titleField.getText()), checkinDT);
                    int threadID = (int) MainActivity.database.threadDao().insertThread(thread);

                    // And create a check in representing this first checkin in the thread
                    CheckIn newCheckin = new CheckIn(threadID, checkinDT, "First Meeting");
                    int checkinID = (int) MainActivity.database.threadDao().insertCheckin(newCheckin);

                    //Finally, add the custom fields created to the database, linked to the thread id
                    layout = findViewById(R.id.form_extra_fields);
                    addFieldData(layout, threadID,checkinID);
                    finish();

                } catch (ParseException e) {
                    e.printStackTrace();
                    Log.e("edebug", "Error reading date data");
                }
            }
        });

    }

    // Gets custom field data from the form
    public void addFieldData(LinearLayout layout, int threadID, int checkinID){
        for (int i = 0; i < layout.getChildCount(); i++) {
            int counter = 0;
            View v = layout.getChildAt(i);
            // Looking to capture data form all Edittexts except the non-custom ones already captured above
            if (v instanceof EditText && (
                    (v.getId() != R.id.editText_q1)
                            && (v.getId() != R.id.editText_q2)
                            && (v.getId() != R.id.editText_q3)
            )) {
                MainActivity.database.threadDao().createFieldData(threadID, checkinID, String.valueOf(((EditText) v).getText()));
                counter++;
            }
        }
    }
}