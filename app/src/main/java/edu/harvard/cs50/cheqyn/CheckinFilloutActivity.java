package edu.harvard.cs50.cheqyn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class CheckinFilloutActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CheckinFilloutAdapter adapter;
    private LinearLayoutManager layoutManager;
    private Button submitButton;

    private TextView titleTextview;
    private int threadId;
    private List<CheckinFields> fields;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkin_fillout);

        threadId = getIntent().getIntExtra("threadId", 0);
        fields = MainActivity.database.threadDao().getFields(threadId);

        recyclerView = findViewById(R.id.checkin_fillout_recycler_view);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new CheckinFilloutAdapter(fields, threadId); //todo add arguments
        recyclerView.setAdapter(adapter);

        titleTextview = findViewById(R.id.checkin_fillout_title);
        titleTextview.setText(getIntent().getStringExtra("checkinTitle"));



        submitButton = findViewById(R.id.checkin_fillout_submitb);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo update field data
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.reload();
    }
}