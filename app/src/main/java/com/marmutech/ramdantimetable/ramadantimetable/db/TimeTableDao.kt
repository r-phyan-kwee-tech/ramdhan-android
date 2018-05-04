package com.marmutech.ramdantimetable.ramadantimetable.db

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.marmutech.ramdantimetable.ramadantimetable.model.TimeTableDay

@Dao
interface TimeTableDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(timeTableDay: TimeTableDay)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun bulkInsert(timetableDays: List<TimeTableDay>)

    @Query("""SELECT * FROM day WHERE stateId=:stateId limit :limit offset :offset """)
    fun getDayByStateId(stateId: String, limit: Int, offset: Int): LiveData<List<TimeTableDay>>

    @Query("""SELECT * FROM day WHERE objectId=:objectId""")
    fun getDayById(objectId: String): LiveData<TimeTableDay>
}