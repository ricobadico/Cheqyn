package edu.harvard.cs50.cheqyn;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {CheckIn.class, CheckInThread.class}, version = 1)
@TypeConverters({Converter.class})
public abstract class ThreadDatabase extends RoomDatabase {
    public abstract ThreadDao threadDao();

}
