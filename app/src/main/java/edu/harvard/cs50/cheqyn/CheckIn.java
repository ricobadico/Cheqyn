package edu.harvard.cs50.cheqyn;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "checkins")
public class CheckIn {
    @PrimaryKey
    public int id;

    @ColumnInfo(name="date")
    public String date;


    //TODO: needs to connect to Checkin somehow. Consider what was done in pokedex assignment
}
