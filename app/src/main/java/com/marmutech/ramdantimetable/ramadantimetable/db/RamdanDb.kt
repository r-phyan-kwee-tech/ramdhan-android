package com.marmutech.ramdantimetable.ramadantimetable.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.marmutech.ramdantimetable.ramadantimetable.model.Country
import com.marmutech.ramdantimetable.ramadantimetable.model.State
import com.marmutech.ramdantimetable.ramadantimetable.model.TimeTableDay


/**
 * Main
 */
@Database(
        entities = [
            Country::class,
            State::class,
            TimeTableDay::class
        ],
        version = 1,
        exportSchema = false
)
abstract class RamdanDb : RoomDatabase() {

    abstract fun countryDao(): CountryDao

    abstract fun stateDao(): StateDao

    abstract fun timetableDao(): TimeTableDao
}

fun offsetManager(limit: Int, page: Int): Int {
    var p = page
    if (p == 0) {
        p = 1
    }
    return (p * limit) - limit
}