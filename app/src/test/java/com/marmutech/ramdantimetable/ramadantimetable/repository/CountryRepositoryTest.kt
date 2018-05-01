package com.marmutech.ramdantimetable.ramadantimetable.repository

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import com.marmutech.ramdantimetable.ramadantimetable.ApiUtil
import com.marmutech.ramdantimetable.ramadantimetable.TestUtil
import com.marmutech.ramdantimetable.ramadantimetable.api.CountryService
import com.marmutech.ramdantimetable.ramadantimetable.db.CountryDao
import com.marmutech.ramdantimetable.ramadantimetable.db.offsetManager
import com.marmutech.ramdantimetable.ramadantimetable.mock
import com.marmutech.ramdantimetable.ramadantimetable.model.*
import com.marmutech.ramdantimetable.ramadantimetable.util.InstantAppExecutors
import com.marmutech.ramdantimetable.ramadantimetable.vo.Resource
import com.nhaarman.mockito_kotlin.verify
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito
import org.mockito.Mockito.*

@RunWith(JUnit4::class)
class CountryRepositoryTest {
    private val countryDao = mock(CountryDao::class.java)
    private val countryService = mock(CountryService::class.java)
    private val repo = CountryRepository(InstantAppExecutors(), countryDao, countryService)

    var query = "{\n" +
            "  countries(limit: 100, page: ${offsetManager(100, 1)}) {\n" +
            "    data {\n" +
            "      id\n" +
            "      objectId\n" +
            "      name\n" +
            "      createdDate\n" +
            "      updatedDate\n" +
            "    }\n" +
            "  }\n" +
            "}"

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun loadCountries() {
        repo.loadCountryList(100, 1)
        verify(countryDao).getCountryList(100, offsetManager(100, 1))
    }

    @Test
    fun goToNetwork() {
        val dbData = MutableLiveData<List<Country>>()
        Mockito.`when`(countryDao!!.getCountryList(100, offsetManager(100, 1))).thenReturn(dbData)
        val mockCountry1 = TestUtil.createCountry("Myanmar")
        val mockCountry2 = TestUtil.createCountry("Singapore")
        val mockCountry3 = TestUtil.createCountry("Malaysia")
        val countries: List<Country> = listOf<Country>(mockCountry1, mockCountry2, mockCountry3)
        val call = CountryResponse(data = Data(countries = Countries(data = countries),
                days = Days(data = listOf(TestUtil.createTimtableDay(1, "1", "1"))), states = States(listOf(TestUtil.createState("123456")))))
        Mockito.`when`(countryService!!.getCountryList(query)).thenReturn(ApiUtil.successCall(call))
        val observer = mock<Observer<Resource<List<Country>>>>()
        repo.loadCountryList(100, offsetManager(100, 1)).observeForever(observer)
        verify(countryService, never()).getCountryList(query)
        val updatedDbData = MutableLiveData<List<Country>>()
        Mockito.`when`(countryDao!!.getCountryList(100, offsetManager(100, 1))).thenReturn(updatedDbData)
        dbData.value = null
        verify(countryService).getCountryList(query)
    }

    @Test
    fun goOffline() {
        val dbData = MutableLiveData<List<Country>>()
        val mockCountry1 = TestUtil.createCountry("Myanmar")
        val mockCountry2 = TestUtil.createCountry("Singapore")
        val mockCountry3 = TestUtil.createCountry("Malaysia")
        val countries: List<Country> = listOf(mockCountry1, mockCountry2, mockCountry3)

        dbData.value = countries
        `when`(countryDao!!.getCountryList(100, offsetManager(100, 1))).thenReturn(dbData)
        val observer = mock<Observer<Resource<List<Country>>>>()
        repo.loadCountryList(100, offsetManager(100, 1)).observeForever(observer)
        verify(countryService, never()).getCountryList(query)
        verify(observer).onChanged(Resource.success(countries))
    }


}