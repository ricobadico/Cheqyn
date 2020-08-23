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
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private  ConversationThreadAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    public static ThreadDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //todo switch these to the database
        List<CheckInThread> myDataset = new ArrayList<>(
        Arrays.asList(
                new CheckInThread("One on One with Jacquard", "May 25, 2021"),
                new CheckInThread("Lulu chat", "December 1, 2020"),
                new CheckInThread("Touch Base with self", "August 23, 2020")
        ));

        // Setting up Recyclerview for list of Conversations
        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new ConversationThreadAdapter(myDataset);
        recyclerView.setAdapter(adapter);

        // Set up database instance
        database = Room
                .databaseBuilder(getApplicationContext(), ThreadDatabase.class, "threads")
                .allowMainThreadQueries()
                .build();

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