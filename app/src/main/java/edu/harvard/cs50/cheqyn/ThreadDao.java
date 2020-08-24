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

    @Query("INSERT INTO checkin_fields (thread_id, inner_id, field_title) VALUES (:threadId, :innerId, :fieldTitle)")
    void createFieldData(int threadId, int innerId, String fieldTitle);

    @Query("DELETE FROM threads WHERE id = :id")
    void delete(int id);

    @Query("SELECT * FROM threads ORDER BY soonestDate")
    List<CheckInThread> getAllThreads();

    @Query("SELECT * FROM checkins WHERE (thread_id = :threadId) AND (date >= :now) ORDER BY date")
    List<CheckIn> getFutureThreadCheckIns(int threadId, Date now);

    @Query("SELECT * FROM checkins WHERE (thread_id = :threadId) AND (date < :now) ORDER BY date DESC")
    List<CheckIn> getPastThreadCheckIns(int threadId, Date now);


    //todo: create a createCheckin which links to thread, updates soonestDate if newer

}
