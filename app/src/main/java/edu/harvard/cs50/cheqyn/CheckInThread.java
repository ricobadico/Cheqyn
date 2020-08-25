package edu.harvard.cs50.cheqyn;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity(tableName = "threads")
public class CheckInThread {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name="title")
    public String title;

    @ColumnInfo(name="soonestDate")
    public Date soonestDate;

//    private List<CheckIn> Checkins = new ArrayList<>();

    //Allows for instantiation of basic thread entity
    public CheckInThread(String title, Date soonestDate){
        this.title = title;
        this.soonestDate = soonestDate;
    }

    //TODO: needs to connect to Checkin somehow. Consider what was done in pokedex assignment

}
