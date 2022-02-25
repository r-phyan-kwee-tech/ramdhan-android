package com.marmutech.ramdantimetable.ramadantimetable.db

import androidx.room.Database
import androidx.room.RoomDatabase
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
    abstract fun timetableDao(): TimeTableDao

    abstract fun countryDao(): CountryDao

    abstract fun stateDao(): StateDao
}

fun offsetManager(limit: Int, page: Int): Int {
    var p = page
    if (p == 0) {
        p = 1
    }
    return (p * limit) - limit
}
