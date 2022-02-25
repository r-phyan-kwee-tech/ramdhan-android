package com.marmutech.ramdantimetable.ramadantimetable.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.marmutech.ramdantimetable.ramadantimetable.model.Country
import kotlinx.coroutines.flow.Flow

@Dao
interface CountryDao {

    @Query("""SELECT * FROM country """)
    fun getCountryList(): Flow<List<Country>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun bulkInsert(countries: List<Country>)
}
