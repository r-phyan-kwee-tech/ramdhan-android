package com.marmutech.ramdantimetable.ramadantimetable.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.marmutech.ramdantimetable.ramadantimetable.model.State
import kotlinx.coroutines.flow.Flow

@Dao
interface StateDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun bulkInsert(states: List<State>)

    //@Query("""SELECT * FROM state WHERE countryId=:countryId limit :limit offset :offset """)
    @Query("SELECT * FROM state WHERE countryId=:countryId")
    fun getStateByCountryId(countryId: String): Flow<List<State>>
}
