package com.marmutech.ramdantimetable.ramadantimetable.repository

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.marmutech.ramdantimetable.ramadantimetable.api.CountryService
import com.marmutech.ramdantimetable.ramadantimetable.db.CountryDao
import com.marmutech.ramdantimetable.ramadantimetable.util.InstantAppExecutors
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.mock

@RunWith(JUnit4::class)
class CountryRepositoryTest {
    private val countryDao = mock(CountryDao::class.java)
    private val countryService = mock(CountryService::class.java)
    private val repo = CountryRepository(InstantAppExecutors(), countryDao, countryService)

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun loadCountries() {
//        repo.loadCountryList(100, 1)
//        verify(countryDao).getCountryList(100, offsetManager(100, 1))
    }

    @Test
    fun goToNetwork() {
//        val dbData = MutableLiveData<List<Country>>()
//        Mockito.`when`(countryDao!!.getCountryList(100, offsetManager(100, 1))).thenReturn(dbData)
//        val mockCountry1 = TestUtil.createCountry("Myanmar")
//        val mockCountry2 = TestUtil.createCountry("Singapore")
//        val mockCountry3 = TestUtil.createCountry("Malaysia")
//
//        val countries: List<Country> = listOf<Country>(mockCountry1, mockCountry2, mockCountry3)
//
//
//
//        val call = CountryResponse(data = Data(countries = Countries(data = countries),
//                days = Days(data = listOf(TestUtil.createTimtableDay(1,"1","1"))),states = States(listOf(TestUtil.createState("123456")))))
//
//        Mockito.`when`(countryService!!.getCountryList("")).thenReturn(ApiUtil.successCall(call))
//
//        val observer = mock<Observer<Resource<CountryResponse>>>()
//
//        repo.loadCountryList(100, offsetManager(100, 1)).observeForever(observer)
//        verify(countryService, never()).getCountryList("")
//        val updatedDbData = MutableLiveData<List<Country>>()
//        Mockito.`when`(countryDao!!.getCountryList(100, offsetManager(100, 1))).thenReturn(updatedDbData)
//        dbData.value = null
//        verify(countryService).getCountryList("")
    }

    @Test
    fun goOffline() {
//        val dbData = MutableLiveData<List<Country>>()
//        val mockCountry1 = TestUtil.createCountry("Myanmar")
//        val mockCountry2 = TestUtil.createCountry("Singapore")
//        val mockCountry3 = TestUtil.createCountry("Malaysia")
//        val countries: List<Country> = listOf(mockCountry1, mockCountry2, mockCountry3)
//
//
//        val call = CountryResponse(data = Data(countries = Countries(data = countries),
//                days = Days(data = listOf(TestUtil.createTimtableDay(1,"1","1"))),states = States(listOf(TestUtil.createState("123456")))))
//
//
//        dbData.value = countries
//        `when`(countryDao!!.getCountryList(100, offsetManager(100, 1))).thenReturn(dbData)
//        val observer = mock<Observer<Resource<CountryResponse>>>()
//        repo.loadCountryList(100, offsetManager(100, 1)).observeForever(observer)
//        verify(countryService, never()).getCountryList("")
//        verify(observer).onChanged(Resource.success(call))
    }


}