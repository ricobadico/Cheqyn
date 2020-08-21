package edu.harvard.cs50.cheqyn;

import androidx.room.RoomDatabase;

public abstract class ThreadDatabase extends RoomDatabase {
    public abstract ThreadDao threadDao();

}
