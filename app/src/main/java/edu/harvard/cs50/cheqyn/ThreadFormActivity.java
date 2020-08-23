package edu.harvard.cs50.cheqyn;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class ThreadFormActivity extends AppCompatActivity {
    private EditText titleField;
    private EditText dateField;
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
                            dateField.setText(dayOfMonth + "/" + (monthOfYear+1) + "/" + year);
                        }
                    }, year, month, day);
                picker.show();
            }
        });

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
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DateFormat df = new SimpleDateFormat("dd/mm/yyyy");
                try {
                    Date checkinDate = df.parse(String.valueOf(dateField.getText()));
                    MainActivity.database.threadDao().create(String.valueOf(titleField.getText()), checkinDate);
                    finish();

                } catch (ParseException e) {
                    e.printStackTrace();
                    Log.e("edebug", "Error reading date data");
                }
            }
        });

    }
}