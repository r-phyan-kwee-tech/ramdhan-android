package com.marmutech.ramdantimetable.ramadantimetable.repository

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.marmutech.ramdantimetable.ramadantimetable.api.TimeTableDayServie
import com.marmutech.ramdantimetable.ramadantimetable.db.TimeTableDao
import com.marmutech.ramdantimetable.ramadantimetable.util.InstantAppExecutors
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.mock

@RunWith(JUnit4::class)
class TimeTableDayRepositoryTest {
    private val timetableDao = mock(TimeTableDao::class.java)
    private val timetableDayService = mock(TimeTableDayServie::class.java)
    private val repo = TimeTableDayRepository(InstantAppExecutors(), timetableDao, timetableDayService)

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun loadCountries() {
//        repo.loadTimetableDayList("9876543210", 100, 1)
//        verify(timetableDao).getDayByStateId("9876543210", 100, offsetManager(100, 1))
    }

    @Test
    fun goToNetwork() {
//        val dbData = MutableLiveData<List<TimeTableDay>>()
//        Mockito.`when`(timetableDao!!.getDayByStateId("9876543210", 100, offsetManager(100, 1))).thenReturn(dbData)
//        val mockCountry1 = TestUtil.createTimtableDay(1, "123456", "9876543210")
//        val mockCountry2 = TestUtil.createTimtableDay(2, "123456", "9876543210")
//        val mockCountry3 = TestUtil.createTimtableDay(3, "123456", "9876543210")
//
//        val timetableDays: List<TimeTableDay> = listOf(mockCountry1, mockCountry2, mockCountry3)
//
////        val call = ApiUtil.successCall(timetableDays)
//        val call = DayResponse(data = Data(countries = Countries(data = listOf(TestUtil.createCountry("demo"))),
//                days = Days(data = timetableDays),states = States(listOf(TestUtil.createState("123456")))))
//
//        Mockito.`when`(timetableDayService!!.getTimetableList("")).thenReturn(ApiUtil.successCall(call))
//
//        val observer = mock<Observer<Resource<DayResponse>>>()
//
//        repo.loadTimetableDayList("9876543210", 100, offsetManager(100, 1)).observeForever(observer)
//        verify(timetableDayService, never()).getTimetableList("")
//        val updatedDbData = MutableLiveData<List<TimeTableDay>>()
//        Mockito.`when`(timetableDao!!.getDayByStateId("9876543210", 100, offsetManager(100, 1))).thenReturn(updatedDbData)
//        dbData.value = null
//        verify(timetableDayService).getTimetableList("")
    }

    @Test
    fun goOffline() {
//        val dbData = MutableLiveData<List<TimeTableDay>>()
//        val mockTimeTable1 = TestUtil.createTimtableDay(1, "123456", "9876543210")
//        val mockTimeTable2 = TestUtil.createTimtableDay(2, "123456", "9876543210")
//        val mockTimeTable3 = TestUtil.createTimtableDay(3, "123456", "9876543210")
//
//        val timetableDays: List<TimeTableDay> = listOf(mockTimeTable1, mockTimeTable2, mockTimeTable3)
//
//
//        val call = DayResponse(data = Data(countries = Countries(data = listOf(TestUtil.createCountry("demo"))),
//                days = Days(data = timetableDays),states = States(listOf(TestUtil.createState("123456")))))
//
//        dbData.value = timetableDays
//        `when`(timetableDao!!.getDayByStateId("9876543210", 100, offsetManager(100, 1))).thenReturn(dbData)
//        val observer = mock<Observer<Resource<DayResponse>>>()
//        repo.loadTimetableDayList("9876543210", 100, offsetManager(100, 1)).observeForever(observer)
//        verify(timetableDayService, never()).getTimetableList("")
//        verify(observer).onChanged(Resource.success(call))
    }


}