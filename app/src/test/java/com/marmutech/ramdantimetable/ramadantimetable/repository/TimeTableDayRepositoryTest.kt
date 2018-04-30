package com.marmutech.ramdantimetable.ramadantimetable.repository

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import com.marmutech.ramdantimetable.ramadantimetable.ApiUtil
import com.marmutech.ramdantimetable.ramadantimetable.TestUtil
import com.marmutech.ramdantimetable.ramadantimetable.api.TimeTableDayServie
import com.marmutech.ramdantimetable.ramadantimetable.db.TimeTableDao
import com.marmutech.ramdantimetable.ramadantimetable.db.offsetManager
import com.marmutech.ramdantimetable.ramadantimetable.mock
import com.marmutech.ramdantimetable.ramadantimetable.model.*
import com.marmutech.ramdantimetable.ramadantimetable.util.InstantAppExecutors
import com.marmutech.ramdantimetable.ramadantimetable.vo.Resource
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.*

@RunWith(JUnit4::class)
class TimeTableDayRepositoryTest {
    private val timetableDao = mock(TimeTableDao::class.java)
    private val timetableDayService = mock(TimeTableDayServie::class.java)
    private val repo = TimeTableDayRepository(InstantAppExecutors(), timetableDao, timetableDayService)

    private var query = "{\n" +
            "  days(limit: 100, page: ${offsetManager(100, 1)}, stateId: \"${9876543210}\") {\n" +
            "    data {\n" +
            "      id\n" +
            "    objectId\n" +
            "    day\n" +
            "    dayMm\n" +
            "    calendarDay\n" +
            "    hijariDay\n" +
            "    sehriTime\n" +
            "    iftariTime\n" +
            "    sehriTimeDesc\n" +
            "    iftariTimeDesc\n" +
            "    sehriTimeDescMmUni\n" +
            "    sehriTimeDescMmZawgyi\n" +
            "    iftariTimeDescMmZawgyi\n" +
            "    iftariTimeDescMmUni\n" +
            "    isKadir\n" +
            "    duaAr\n" +
            "    duaEn\n" +
            "    duaMmUni\n" +
            "    duaMmZawgyi\n" +
            "    countryId\n" +
            "    stateId\n" +
            "    createdDate\n" +
            "    updatedDate\n" +
            "    }\n" +
            "  }\n" +
            "}"

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun loadCountries() {
        repo.loadTimetableDayList("9876543210", 100, 1)
        verify(timetableDao).getDayByStateId("9876543210", 100, offsetManager(100, 1))
    }

    @Test
    fun goToNetwork() {

        val dbData = MutableLiveData<List<TimeTableDay>>()
        `when`(timetableDao!!.getDayByStateId("9876543210", 100, offsetManager(100, 1))).thenReturn(dbData)
        val mockCountry1 = TestUtil.createTimtableDay(1, "123456", "9876543210")
        val mockCountry2 = TestUtil.createTimtableDay(2, "123456", "9876543210")
        val mockCountry3 = TestUtil.createTimtableDay(3, "123456", "9876543210")
        val timetableDays: List<TimeTableDay> = listOf(mockCountry1, mockCountry2, mockCountry3)

        val call = DayResponse(data = Data(countries = Countries(data = listOf(TestUtil.createCountry("demo"))),
                days = Days(data = timetableDays), states = States(listOf(TestUtil.createState("123456")))))

        `when`(timetableDayService!!.getTimetableList(query)).thenReturn(ApiUtil.successCall(call))
        val observer = mock<Observer<Resource<List<TimeTableDay>>>>()

        repo.loadTimetableDayList("9876543210", 100, offsetManager(100, 1)).observeForever(observer)
        verify(timetableDayService, never()).getTimetableList(query)
        val updatedDbData = MutableLiveData<List<TimeTableDay>>()
        `when`(timetableDao.getDayByStateId("9876543210", 100, offsetManager(100, 1))).thenReturn(updatedDbData)
        dbData.value = null
        verify(timetableDayService).getTimetableList(query)
    }

    @Test
    fun goOffline() {
        val dbData = MutableLiveData<List<TimeTableDay>>()
        val mockTimeTable1 = TestUtil.createTimtableDay(1, "123456", "9876543210")
        val mockTimeTable2 = TestUtil.createTimtableDay(2, "123456", "9876543210")
        val mockTimeTable3 = TestUtil.createTimtableDay(3, "123456", "9876543210")

        val timetableDays: List<TimeTableDay> = listOf(mockTimeTable1, mockTimeTable2, mockTimeTable3)

        dbData.value = timetableDays
        `when`(timetableDao!!.getDayByStateId("9876543210", 100, offsetManager(100, 1))).thenReturn(dbData)
        val observer = mock<Observer<Resource<List<TimeTableDay>>>>()
        repo.loadTimetableDayList("9876543210", 100, offsetManager(100, 1)).observeForever(observer)
        verify(timetableDayService, never()).getTimetableList(query)
        verify(observer).onChanged(Resource.success(timetableDays))

    }


}