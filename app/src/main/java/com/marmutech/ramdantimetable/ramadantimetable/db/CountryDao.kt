package com.marmutech.ramdantimetable.ramadantimetable.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.marmutech.ramdantimetable.ramadantimetable.model.Country

@Dao
interface CountryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(country: Country)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun bulkInsert(countries: List<Country>)

    @Query("""SELECT * FROM country limit :limit offset :offset """)
    fun getCountryList(limit: Int, offset: Int): LiveData<List<Country>>

    @Query(""" SELECT * FROM country WHERE objectId=:id""")
    fun getCountryById(id: String): LiveData<Country>


}