package edu.harvard.cs50.cheqyn;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity(tableName = "checkins")
public class CheckIn {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name="date")
    public Date date;

    @ColumnInfo(name="thread_id")
    public int threadId;

    @ColumnInfo(name="Description")
    public String description;

    @ColumnInfo(name="default_notes_title", defaultValue = "General Notes")
    public String defaultNotesTitle;

    @ColumnInfo(name = "default_notes_data", defaultValue = "Add notes here.")
    public String defaultNotesData;


    //TODO: needs to connect to Checkin somehow. Consider what was done in pokedex assignment
}
