package edu.harvard.cs50.cheqyn;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ThreadDao {

    @Query("INSERT INTO threads (title, soonestDate) VALUES ('New Conversation', 'Select a Date')")
    void create();
    //TODO change hardcoded values to something inputted into the create method
    // TODO: set up a place where we get these values,then call create


    @Query("DELETE FROM threads WHERE id = :id")
    void delete(int id);

    @Query("SELECT * FROM threads")
    List<CheckInThread> getAllThreads();
}
