package edu.harvard.cs50.cheqyn;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private  ConversationThreadAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    public static ThreadDatabase database;

    //For second list, past checkins
    private RecyclerView recyclerView2;
    private  ConversationThreadAdapter adapter2;
    private RecyclerView.LayoutManager layoutManager2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set up database instance
        database = Room
                .databaseBuilder(getApplicationContext(), ThreadDatabase.class, "threads")
                .allowMainThreadQueries()
                .build();
        //todo switch these to the database
        List<CheckInThread> myDataset = MainActivity.database.threadDao().getAllFutureThreads(Calendar.getInstance().getTime());
        List<CheckInThread> myPastDataset = MainActivity.database.threadDao().getAllFutureThreads(Calendar.getInstance().getTime());

        // Setting up Recyclerview for list of Conversations
        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ConversationThreadAdapter(myDataset);
        recyclerView.setAdapter(adapter);

        // Setting up Recyclerview for list of Past Conversations
        recyclerView2 = (RecyclerView)findViewById(R.id.recycler_view2);
        recyclerView2.setHasFixedSize(true);
        layoutManager2 = new LinearLayoutManager(this);
        recyclerView2.setLayoutManager(layoutManager2);
        adapter2 = new ConversationThreadAdapter(myPastDataset);
        recyclerView2.setAdapter(adapter2);



        // Setting up button which creates new thread
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(view.getContext(), ThreadFormActivity.class);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}