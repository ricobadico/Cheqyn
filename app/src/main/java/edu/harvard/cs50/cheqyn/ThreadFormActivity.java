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
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class ThreadFormActivity extends AppCompatActivity {
    private EditText titleField;
    private EditText dateField;
    private EditText timeField;
    private Date dateData;
    DatePickerDialog picker;
    private Button submitButton;
    private Button newFieldButton;
    private static int fieldCounter;


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
        ThreadFormActivity.fieldCounter = 0;
        newFieldButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                LinearLayout myLayout = findViewById(R.id.form_extra_fields);

                EditText addEditTitle = new EditText(ThreadFormActivity.this);
                addEditTitle.setLayoutParams(new RelativeLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT));
                addEditTitle.setHint("Add title here");
                addEditTitle.setBackgroundColor(Color.parseColor("#CCCCCC"));
                addEditTitle.setEms(15);

                myLayout.addView(addEditTitle);
                ThreadFormActivity.fieldCounter++;
            }
        });


        submitButton = findViewById(R.id.form_button1);
        titleField = findViewById(R.id.editText_q1);
        dateField = findViewById(R.id.editText_q2);
        timeField = findViewById(R.id.editText_q3);


        // Submitting everything present on page to database
        submitButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                // Must convert to a single datetime
                DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm");
                try {
                    String checkinDate = String.valueOf(dateField.getText());
                    String checkinTime = String.valueOf(timeField.getText());
//                    Date checkinDate = df.parse(String.valueOf(dateField.getText()));
//                    Date checkinTime = tf.parse(String.valueOf(timeField.getText()));
                    Log.d("edebugb", checkinDate);
                    Log.d("edebugb", checkinTime);
                    Date checkinDT = df.parse(checkinDate + " " + checkinTime);
                    assert checkinDT != null;
                    Log.d("edebugb", (checkinDT.toString()));

                    // Now update database with text fields
                    MainActivity.database.threadDao().create(String.valueOf(titleField.getText()), checkinDT);
                    finish();

                } catch (ParseException e) {
                    e.printStackTrace();
                    Log.e("edebug", "Error reading date data");
                }
            }
        });

    }
}