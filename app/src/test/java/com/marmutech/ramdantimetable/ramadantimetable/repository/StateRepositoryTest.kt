package com.marmutech.ramdantimetable.ramadantimetable.repository

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import com.marmutech.ramdantimetable.ramadantimetable.ApiUtil
import com.marmutech.ramdantimetable.ramadantimetable.TestUtil
import com.marmutech.ramdantimetable.ramadantimetable.api.StateService
import com.marmutech.ramdantimetable.ramadantimetable.db.StateDao
import com.marmutech.ramdantimetable.ramadantimetable.db.offsetManager
import com.marmutech.ramdantimetable.ramadantimetable.mock
import com.marmutech.ramdantimetable.ramadantimetable.model.State
import com.marmutech.ramdantimetable.ramadantimetable.util.InstantAppExecutors
import com.marmutech.ramdantimetable.ramadantimetable.vo.Resource
import com.nhaarman.mockito_kotlin.never
import com.nhaarman.mockito_kotlin.verify
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

@RunWith(JUnit4::class)
class StateRepositoryTest {
    private val stateDao = mock(StateDao::class.java)
    private val stateService = mock(StateService::class.java)
    private val repo = StateRepository(InstantAppExecutors(), stateDao, stateService)

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun loadStates() {
        repo.loadStateList("12345678890", 100, 1)
        verify(stateDao).getStateByCountryId("12345678890", 100, offsetManager(100, 1))
    }

    @Test
    fun goToNetwork() {
        val dbData = MutableLiveData<List<State>>()
        Mockito.`when`(stateDao!!.getStateByCountryId("12345678890", 100, offsetManager(100, 1))).thenReturn(dbData)
        val mockState1 = TestUtil.createState("12345678890")
        val mockState2 = TestUtil.createState("12345678890")
        val mockState3 = TestUtil.createState("12345678890")

        val states: List<State> = listOf(mockState1, mockState2, mockState3)

        val call = ApiUtil.successCall(states)
        Mockito.`when`(stateService!!.getStateList("")).thenReturn(call)

        val observer = mock<Observer<Resource<List<State>>>>()

        repo.loadStateList("12345678890", 100, offsetManager(100, 1)).observeForever(observer)
        verify(stateService, never()).getStateList("")
        val updatedDbData = MutableLiveData<List<State>>()
        Mockito.`when`(stateDao!!.getStateByCountryId("12345678890", 100, offsetManager(100, 1))).thenReturn(updatedDbData)
        dbData.value = null
        verify(stateService).getStateList("")
    }

    @Test
    fun goOffline() {
        val dbData = MutableLiveData<List<State>>()
        val mockState1 = TestUtil.createState("12345678890")
        val mockState2 = TestUtil.createState("12345678890")
        val mockState3 = TestUtil.createState("12345678890")
        val states: List<State> = listOf(mockState1, mockState2, mockState3)

        dbData.value = states
        `when`(stateDao!!.getStateByCountryId("1234567890", 100, offsetManager(100, 1))).thenReturn(dbData)
        val observer = mock<Observer<Resource<List<State>>>>()
        repo.loadStateList("1234567890", 100, offsetManager(100, 1)).observeForever(observer)
        verify(stateService, never()).getStateList("")
        verify(observer).onChanged(Resource.success(states))
    }


}