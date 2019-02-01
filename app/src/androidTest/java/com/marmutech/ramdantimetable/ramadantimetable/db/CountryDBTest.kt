package com.marmutech.ramdantimetable.ramadantimetable.db

import androidx.test.runner.AndroidJUnit4
import com.marmutech.ramdantimetable.ramadantimetable.LiveDataTestUtil.getValue
import com.marmutech.ramdantimetable.ramadantimetable.TestUtil
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CountryDBTest : DbTest() {

    @Test
    fun insertAndRead() {
        val mockState = TestUtil.createCountry("Myanmar")
        db.countryDao().insert(mockState)
        val loaded = getValue(db.countryDao().getCountryById(mockState.objectId))
        MatcherAssert.assertThat(loaded, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(loaded.objectId, CoreMatchers.`is`(mockState.objectId))
        MatcherAssert.assertThat(loaded.name, CoreMatchers.`is`(mockState.name))
    }

    @Test
    fun insertMultiple() {
        val mockState1 = TestUtil.createCountry("Myanmar")
        val mockState2 = TestUtil.createCountry("Singapore")
        val mockState3 = TestUtil.createCountry("Malaysia")

        db.beginTransaction()
        try {
            db.countryDao().bulkInsert(arrayListOf(mockState1, mockState2, mockState3))
            db.setTransactionSuccessful()
        } finally {
            db.endTransaction()
        }
        val list = getValue(db.countryDao().getCountryList(3, 0))
        MatcherAssert.assertThat(list.size, CoreMatchers.`is`(3))
        val first = list[0]

        MatcherAssert.assertThat(first.objectId, CoreMatchers.`is`(mockState1.objectId))
        MatcherAssert.assertThat(first.name, CoreMatchers.`is`(mockState1.name))

        val second = list[1]
        MatcherAssert.assertThat(second.objectId, CoreMatchers.`is`(mockState2.objectId))
        MatcherAssert.assertThat(second.name, CoreMatchers.`is`(mockState2.name))

        val third = list[2]
        MatcherAssert.assertThat(third.objectId, CoreMatchers.`is`(mockState3.objectId))
        MatcherAssert.assertThat(third.name, CoreMatchers.`is`(mockState3.name))
    }
}