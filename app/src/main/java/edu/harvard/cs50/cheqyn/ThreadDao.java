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

    @Insert(onConflict = OnConflictStrategy.IGNORE) //todo maybe replace with .REPLACE
    long insertCheckin(CheckIn checkIn);

    @Query("INSERT INTO checkin_fields (thread_id, checkin_id, field_title) VALUES (:threadId, :checkinId, :fieldTitle)")
    void createFieldData(int threadId, int checkinId, String fieldTitle);

    @Query("SELECT * FROM threads ORDER BY soonestDate DESC")
    List<CheckInThread> getAllThreads();

    @Query("SELECT * FROM threads WHERE soonestDate >= :now ORDER BY soonestDate DESC")
    List<CheckInThread> getAllFutureThreads(Date now);

    @Query("SELECT * FROM threads WHERE soonestDate < :now ORDER BY soonestDate DESC")
    List<CheckInThread> getAllPastThreads(Date now);

    @Query("SELECT * FROM checkins WHERE (thread_id = :threadId) AND (date >= :now) ORDER BY date")
    List<CheckIn> getFutureThreadCheckIns(int threadId, Date now);

    @Query("SELECT * FROM checkins WHERE (thread_id = :threadId) AND (date < :now) ORDER BY date DESC")
    List<CheckIn> getPastThreadCheckIns(int threadId, Date now);

    @Query("SELECT * FROM checkin_fields WHERE checkin_id = :checkinId")
    List<CheckinFields> getFields(int checkinId);

    @Query("UPDATE checkin_fields SET field_data = :contents WHERE (checkin_id = :checkinId) AND (field_title = :title)")
    void saveFieldData(String contents, int checkinId, String title);

    @Query("SELECT date FROM checkins WHERE (thread_id = :threadId) AND (date > :now) ORDER BY date LIMIT 1")
    Date getSoonestDate(int threadId, Date now);

    @Query("UPDATE threads SET soonestDate = :date WHERE id = :id")
    void updateThreadSoonestDate(Date date, int id);

// for debugging
//    @Query("SELECT * FROM checkin_fields")
//    List<CheckinFields> getAllFields();

    //todo: create a createCheckin which links to thread, updates soonestDate if newer

}
