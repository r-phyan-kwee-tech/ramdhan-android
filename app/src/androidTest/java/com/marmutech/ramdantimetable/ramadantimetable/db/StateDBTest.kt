package com.marmutech.ramdantimetable.ramadantimetable.db

import androidx.test.runner.AndroidJUnit4
import com.marmutech.ramdantimetable.ramadantimetable.LiveDataTestUtil.getValue
import com.marmutech.ramdantimetable.ramadantimetable.TestUtil
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class StateDBTest : DbTest() {
    @Test
    fun insertAndRead() {
        val mockState = TestUtil.createState("123456")
        db.stateDao().insert(mockState)
        val loaded = getValue(db.stateDao().getStateById(mockState.objectId))
        MatcherAssert.assertThat(loaded, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(loaded.objectId, CoreMatchers.`is`(mockState.objectId))
        MatcherAssert.assertThat(loaded.countryId, CoreMatchers.`is`(mockState.countryId))
        MatcherAssert.assertThat(loaded.nameMmUni, CoreMatchers.`is`(mockState.nameMmUni))
        MatcherAssert.assertThat(loaded.nameMmZawgyi, CoreMatchers.`is`(mockState.nameMmZawgyi))
    }

    @Test
    fun insertMultiple() {
        val mockState1 = TestUtil.createState("123456")
        val mockState2 = TestUtil.createState("123456")
        val mockState3 = TestUtil.createState("123456")

        db.beginTransaction()
        try {
            db.stateDao().bulkInsert(arrayListOf(mockState1, mockState2, mockState3))
            db.setTransactionSuccessful()
        } finally {
            db.endTransaction()
        }
        val list = getValue(db.stateDao().getStateByCountryId("123456", 3, 0))
        MatcherAssert.assertThat(list.size, CoreMatchers.`is`(3))
        val first = list[0]

        MatcherAssert.assertThat(first.objectId, CoreMatchers.`is`(mockState1.objectId))
        MatcherAssert.assertThat(first.countryId, CoreMatchers.`is`(mockState1.countryId))
        MatcherAssert.assertThat(first.nameMmUni, CoreMatchers.`is`(mockState1.nameMmUni))
        MatcherAssert.assertThat(first.nameMmZawgyi, CoreMatchers.`is`(mockState1.nameMmZawgyi))

        val second = list[1]
        MatcherAssert.assertThat(second.objectId, CoreMatchers.`is`(mockState2.objectId))
        MatcherAssert.assertThat(second.countryId, CoreMatchers.`is`(mockState2.countryId))
        MatcherAssert.assertThat(second.nameMmUni, CoreMatchers.`is`(mockState2.nameMmUni))
        MatcherAssert.assertThat(second.nameMmZawgyi, CoreMatchers.`is`(mockState2.nameMmZawgyi))

        val third = list[2]
        MatcherAssert.assertThat(third.objectId, CoreMatchers.`is`(mockState3.objectId))
        MatcherAssert.assertThat(third.countryId, CoreMatchers.`is`(mockState3.countryId))
        MatcherAssert.assertThat(third.nameMmUni, CoreMatchers.`is`(mockState3.nameMmUni))
        MatcherAssert.assertThat(third.nameMmZawgyi, CoreMatchers.`is`(mockState3.nameMmZawgyi))
    }


}