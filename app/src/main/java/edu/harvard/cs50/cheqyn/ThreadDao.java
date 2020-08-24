package edu.harvard.cs50.cheqyn;

import androidx.room.ColumnInfo;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.Date;
import java.util.List;

@Dao
public interface ThreadDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE) //todo maybe replace with .REPLACE
    long insertThread(CheckInThread thread);

    @Query("INSERT INTO checkins (thread_id, date, description) VALUES (:threadId, :date, :description)")
    void createCheckin(int threadId, Date date, String description);


    @Query("DELETE FROM threads WHERE id = :id")
    void delete(int id);

//    @Query("SELECT * FROM threads WHERE soonestDate >= :now ORDER BY soonestDate")
    @Query("SELECT * FROM threads ORDER BY soonestDate")
    List<CheckInThread> getAllThreads();

    @Query("SELECT * FROM checkins WHERE thread_id = :threadId ORDER BY date")
    List<CheckIn> getThreadCheckIns(int threadId);

    //todo: create getAllFuture and getAllPast, sort by date

    //todo: create a createCheckin which links to thread, updates soonestDate if newer

}
