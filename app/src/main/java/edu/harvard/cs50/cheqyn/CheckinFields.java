package edu.harvard.cs50.cheqyn;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "checkin_fields")
public class CheckinFields {
        @PrimaryKey(autoGenerate = true)
        public int id;

        // Used for attaching to a particular thread
        @ColumnInfo(name = "checkin_id")
        public int checkinId;

        // Used fir iterating over fields within class
        @ColumnInfo(name = "thread_id")
        public int threadId;

        // List of fields to fill out
        @ColumnInfo(name="field_title")
        public String fieldTitle;

        @ColumnInfo(name = "field_data")
        public String fieldData;
}
