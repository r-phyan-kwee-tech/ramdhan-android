package com.marmutech.ramdantimetable.ramadantimetable.db

import androidx.test.runner.AndroidJUnit4
import com.marmutech.ramdantimetable.ramadantimetable.LiveDataTestUtil.getValue
import com.marmutech.ramdantimetable.ramadantimetable.TestUtil
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TimeTableDayDBTest : DbTest() {

    @Test
    fun insertAndRead() {
        val timeTableDay = TestUtil.createTimtableDay(1, "123456", "654321")
        db.timetableDao().insert(timeTableDay)
        val loaded = getValue(db.timetableDao().getDayById(timeTableDay.objectId))
        assertThat(loaded, CoreMatchers.notNullValue())
        assertThat(loaded.objectId, `is`(timeTableDay.objectId))
        assertThat(loaded.calendarDay, `is`(timeTableDay.calendarDay))
        assertThat(loaded.countryId, `is`(timeTableDay.countryId))
        assertThat(loaded.stateId, `is`(timeTableDay.stateId))
    }

    @Test
    fun insertMultiple() {
        val timeTableDay1 = TestUtil.createTimtableDay(1, "2F95FDBA-5359-4D47-BED0-02535E965AC2", "97BA5509-C222-4ED4-B480-CAE844FFC8D6")
        val timeTableDay2 = TestUtil.createTimtableDay(2, "2F95FDBA-5359-4D47-BED0-02535E965AC2", "97BA5509-C222-4ED4-B480-CAE844FFC8D6")
        val timeTableDay3 = TestUtil.createTimtableDay(3, "2F95FDBA-5359-4D47-BED0-02535E965AC2", "97BA5509-C222-4ED4-B480-CAE844FFC8D6")
        db.beginTransaction()
        try {
            db.timetableDao().bulkInsert(arrayListOf(timeTableDay1, timeTableDay2, timeTableDay3))
            db.setTransactionSuccessful()
        } finally {
            db.endTransaction()
        }
        val list = getValue(db.timetableDao().getDayByStateId("97BA5509-C222-4ED4-B480-CAE844FFC8D6", 3, 0))
        assertThat(list.size, `is`(3))
        val first = list[0]

        assertThat(first.day, `is`(1))
        assertThat(first.duaAr, `is`(timeTableDay1.duaAr))
        assertThat(first.duaEn, `is`(timeTableDay1.duaEn))
        assertThat(first.dayMm, `is`(timeTableDay1.dayMm))

        val second = list[1]
        assertThat(second.day, `is`(2))
        assertThat(second.duaAr, `is`(timeTableDay2.duaAr))
        assertThat(second.duaEn, `is`(timeTableDay2.duaEn))

        val third = list[2]
        assertThat(third.day, `is`(3))
        assertThat(third.duaAr, `is`(timeTableDay3.duaAr))
        assertThat(third.dayMm, `is`(timeTableDay3.dayMm))
    }
}