package com.marmutech.ramdantimetable.ramadantimetable.db

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
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