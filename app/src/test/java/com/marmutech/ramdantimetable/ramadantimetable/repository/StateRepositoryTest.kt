package com.marmutech.ramdantimetable.ramadantimetable.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.marmutech.ramdantimetable.ramadantimetable.ApiUtil
import com.marmutech.ramdantimetable.ramadantimetable.TestUtil
import com.marmutech.ramdantimetable.ramadantimetable.api.ApiService
import com.marmutech.ramdantimetable.ramadantimetable.db.StateDao
import com.marmutech.ramdantimetable.ramadantimetable.db.offsetManager
import com.marmutech.ramdantimetable.ramadantimetable.mock
import com.marmutech.ramdantimetable.ramadantimetable.model.Countries
import com.marmutech.ramdantimetable.ramadantimetable.model.Data
import com.marmutech.ramdantimetable.ramadantimetable.model.Days
import com.marmutech.ramdantimetable.ramadantimetable.model.State
import com.marmutech.ramdantimetable.ramadantimetable.model.StateResponse
import com.marmutech.ramdantimetable.ramadantimetable.model.States
import com.marmutech.ramdantimetable.ramadantimetable.util.InstantAppExecutors
import com.marmutech.ramdantimetable.ramadantimetable.vo.Resource
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.Mockito.never
import org.mockito.Mockito.verify

@RunWith(JUnit4::class)
class StateRepositoryTest {
    private val stateDao = mock(StateDao::class.java)
    private val stateService = mock(ApiService::class.java)
    private val repo = StateRepository(InstantAppExecutors(), stateDao, stateService)

    var query = "{\n" +
            "  states(limit: 100, page: ${offsetManager(100, 1)}, countryId: \"${12345678890}\") {\n" +
            "    data {\n" +
            "      id\n" +
            "      objectId\n" +
            "      nameMmUni\n" +
            "      nameMmZawgyi\n" +
            "      countryId\n" +
            "      createdDate\n" +
            "      updatedDate\n" +
            "    }\n" +
            "  }\n" +
            "}"

    var detailQuery = "{\n" +
            "  state(stateId: \"${123456789}\") {\n" +
            "    id\n" +
            "    objectId\n" +
            "    nameMmUni\n" +
            "    nameMmZawgyi\n" +
            "    countryId\n" +
            "    createdDate\n" +
            "    updatedDate\n" +
            "  }\n" +
            "}"

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun loadStates() {
        repo.loadStateList("12345678890", 100, 1)
        verify(stateDao).getStateByCountryId("12345678890", 100, offsetManager(100, 1))
    }

    @Test
    fun loadState() {
        repo.loadState("12345678890")
        verify(stateDao).getStateById("12345678890")
    }


    @Test
    fun loadStateListFromNetwork() {
        val dbData = MutableLiveData<List<State>>()
        Mockito.`when`(stateDao!!.getStateByCountryId("12345678890", 100, offsetManager(100, 1))).thenReturn(dbData)
        val mockState1 = TestUtil.createState("12345678890")
        val mockState2 = TestUtil.createState("12345678890")
        val mockState3 = TestUtil.createState("12345678890")

        val states: List<State> = listOf(mockState1, mockState2, mockState3)


        val call = StateResponse(data = Data(countries = Countries(data = listOf(TestUtil.createCountry("Mynmar"))),
                days = Days(data = listOf(TestUtil.createTimtableDay(1, "1", "1"))),
                states = States(data = states),
                country = TestUtil.createCountry("a"), state = TestUtil.createState("1"), day = TestUtil.createTimtableDay(1, "1", "1")))
        Mockito.`when`(stateService!!.getStateList(query)).thenReturn(ApiUtil.successCall(call))
        val observer = mock<Observer<Resource<List<State>>>>()
        repo.loadStateList("12345678890", 100, offsetManager(100, 1)).observeForever(observer)
        verify(stateService, never()).getStateList(query)
        val updatedDbData = MutableLiveData<List<State>>()
        Mockito.`when`(stateDao!!.getStateByCountryId("12345678890", 100, offsetManager(100, 1))).thenReturn(updatedDbData)
        dbData.value = null
        verify(stateService).getStateList(query)
    }


    @Test
    fun loadStatesOffline() {
        val dbData = MutableLiveData<List<State>>()
        val mockState1 = TestUtil.createState("12345678890")
        val mockState2 = TestUtil.createState("12345678890")
        val mockState3 = TestUtil.createState("12345678890")
        val states: List<State> = listOf(mockState1, mockState2, mockState3)

        dbData.value = states
        `when`(stateDao!!.getStateByCountryId("1234567890", 100, offsetManager(100, 1))).thenReturn(dbData)
        val observer = mock<Observer<Resource<List<State>>>>()
        repo.loadStateList("1234567890", 100, offsetManager(100, 1)).observeForever(observer)
        verify(stateService, never()).getStateList(query)
        verify(observer).onChanged(Resource.success(states))
    }

    @Test
    fun loadStateFromNetwork() {
        val dbData = MutableLiveData<State>()
        Mockito.`when`(stateDao!!.getStateById("123456789")).thenReturn(dbData)
        val mockState1 = TestUtil.createState("12345678890")

        val states: State = mockState1

        val call = StateResponse(data = Data(countries = Countries(data = listOf(TestUtil.createCountry("Mynmar"))),
                days = Days(data = listOf(TestUtil.createTimtableDay(1, "1", "1"))),
                states = States(data = listOf(states)),
                country = TestUtil.createCountry("a"), state = states, day = TestUtil.createTimtableDay(1, "1", "1")))
        Mockito.`when`(stateService!!.getState(detailQuery)).thenReturn(ApiUtil.successCall(call))
        val observer = mock<Observer<Resource<State>>>()
        repo.loadState("123456789").observeForever(observer)
        verify(stateService, never()).getState(detailQuery)
        val updatedDbData = MutableLiveData<State>()
        Mockito.`when`(stateDao!!.getStateById("123456789")).thenReturn(updatedDbData)
        dbData.value = null
        verify(stateService).getState(detailQuery)
    }


    @Test
    fun loadStateOffline() {
        val dbData = MutableLiveData<State>()
        val mockState1 = TestUtil.createState("12345678890")
        val states: State = mockState1

        dbData.value = states
        `when`(stateDao!!.getStateById("123456789")).thenReturn(dbData)
        val observer = mock<Observer<Resource<State>>>()
        repo.loadState("123456789").observeForever(observer)
        verify(stateService, never()).getStateList(detailQuery)
        verify(observer).onChanged(Resource.success(states))
    }

}
