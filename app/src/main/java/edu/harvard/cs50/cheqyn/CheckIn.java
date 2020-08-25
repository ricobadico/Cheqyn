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

    @ColumnInfo(name="thread_id")
    public int threadId;

    @ColumnInfo(name="date")
    public Date date;

    @ColumnInfo(name="description")
    public String description;


    public CheckIn(int threadId, Date date, String description){
        this.threadId = threadId;
        this.date = date;
        this.description = description;
    }


    //TODO: needs to connect to Checkin somehow. Consider what was done in pokedex assignment
}
