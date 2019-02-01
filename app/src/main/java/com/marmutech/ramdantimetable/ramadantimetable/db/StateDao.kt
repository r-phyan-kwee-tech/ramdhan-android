package com.marmutech.ramdantimetable.ramadantimetable.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.marmutech.ramdantimetable.ramadantimetable.model.State

@Dao
interface StateDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(state: State)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun bulkInsert(states: List<State>)

    @Query("""SELECT * FROM state WHERE countryId=:countryId limit :limit offset :offset """)
    fun getStateByCountryId(countryId: String, limit: Int, offset: Int): LiveData<List<State>>

    @Query("""SELECT * FROM state WHERE objectId=:objectId""")
    fun getStateById(objectId: String): LiveData<State>
}