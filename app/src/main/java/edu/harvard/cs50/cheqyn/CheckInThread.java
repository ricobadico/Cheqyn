package edu.harvard.cs50.cheqyn;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "thread")
public class CheckInThread {

    @PrimaryKey
    public int id;

    @ColumnInfo(name="title")
    public String title;

    @ColumnInfo(name="soonestDate")
    public String soonestDate;

    private List<CheckIn> Checkins = new ArrayList<>();

    //TODO remove this constructor once the database takes care of it
    public CheckInThread(String title, String soonestDate){
        this.title = title;
        this.soonestDate = soonestDate;
    }

    //TODO: needs to connect to Checkin somehow. Consider what was done in pokedex assignment

}
