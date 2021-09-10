package com.marmutech.ramdantimetable.ramadantimetable.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.marmutech.ramdantimetable.ramadantimetable.model.TimeTableDay
import kotlinx.coroutines.flow.Flow

@Dao
interface TimeTableDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(timeTableDay: TimeTableDay)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun bulkInsert(timetableDays: List<TimeTableDay>)

    //todo delete legacy
    @Query("SELECT * FROM day WHERE day.calendarDay BETWEEN (SELECT strftime('%Y/%m/%d', 'now') ) AND (SELECT strftime('%Y/%m/%d',date('now','+365 days'))) AND stateId=:stateId  limit :limit offset :offset ")
    fun getDayByStateId(stateId: String, limit: Int, offset: Int): LiveData<List<TimeTableDay>>

    @Query("SELECT * FROM day WHERE day.calendarDay BETWEEN (SELECT strftime('%Y/%m/%d', 'now') ) AND (SELECT strftime('%Y/%m/%d',date('now','+365 days'))) AND stateId=:stateId")
    fun getDayByStateId(stateId: String): Flow<List<TimeTableDay>>

    /*@Query("SELECT * FROM day WHERE  stateId=:stateId")
    fun getDayByStateId(stateId: String): Flow<List<TimeTableDay>>*/

    @Query("""SELECT * FROM day WHERE objectId=:objectId""")
    fun getDayById(objectId: String): LiveData<TimeTableDay>
}
