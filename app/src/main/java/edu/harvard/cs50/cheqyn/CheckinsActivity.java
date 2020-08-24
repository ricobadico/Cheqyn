package edu.harvard.cs50.cheqyn;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class CheckinsActivity extends AppCompatActivity {

    //For first list, upcoming checkins
    private RecyclerView recyclerView;
    private  CheckinsAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    //For second list, past checkins
    private RecyclerView recyclerView2;
    private  CheckinsAdapter adapter2;
    private RecyclerView.LayoutManager layoutManager2;

    private TextView threadTitle;
    private int threadId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkins);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        threadId = getIntent().getIntExtra("id", 0);
        List<CheckIn> checkinsData = new ArrayList<>();

        List<CheckIn> checkinsPastData = new ArrayList<>();
        checkinsPastData = MainActivity.database.threadDao().getPastThreadCheckIns(threadId, Calendar.getInstance().getTime());

        // Setting up Recyclerview for list of Future Conversations
        recyclerView = (RecyclerView)findViewById(R.id.thread_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new CheckinsAdapter(checkinsData, threadId);
        recyclerView.setAdapter(adapter);

        // Setting up Recyclerview for list of Past Conversations
        recyclerView2 = (RecyclerView)findViewById(R.id.thread_recycler_view2);
        recyclerView2.setHasFixedSize(true);
        layoutManager2 = new LinearLayoutManager(this);
        recyclerView2.setLayoutManager(layoutManager2);
        adapter2 = new CheckinsAdapter(checkinsPastData, threadId);
        recyclerView2.setAdapter(adapter2);


        threadTitle = findViewById(R.id.thread_title_textview);
        threadTitle.setText(getIntent().getStringExtra("title"));


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(view.getContext(), CheckinFormActivity.class);
                intent.putExtra("threadId", threadId);

                context.startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
        adapter.reload();
        adapter2.reload2();
    }
}