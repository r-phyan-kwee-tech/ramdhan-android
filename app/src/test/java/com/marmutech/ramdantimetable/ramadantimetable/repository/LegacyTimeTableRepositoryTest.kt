package com.marmutech.ramdantimetable.ramadantimetable.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.marmutech.ramdantimetable.ramadantimetable.ApiUtil
import com.marmutech.ramdantimetable.ramadantimetable.TestUtil
import com.marmutech.ramdantimetable.ramadantimetable.api.LegacyApiService
import com.marmutech.ramdantimetable.ramadantimetable.db.LegacyCountryDao
import com.marmutech.ramdantimetable.ramadantimetable.db.offsetManager
import com.marmutech.ramdantimetable.ramadantimetable.mock
import com.marmutech.ramdantimetable.ramadantimetable.model.Countries
import com.marmutech.ramdantimetable.ramadantimetable.model.Country
import com.marmutech.ramdantimetable.ramadantimetable.model.CountryResponse
import com.marmutech.ramdantimetable.ramadantimetable.model.Data
import com.marmutech.ramdantimetable.ramadantimetable.model.Days
import com.marmutech.ramdantimetable.ramadantimetable.model.States
import com.marmutech.ramdantimetable.ramadantimetable.util.InstantAppExecutors
import com.marmutech.ramdantimetable.ramadantimetable.vo.Resource
import com.nhaarman.mockito_kotlin.verify
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.Mockito.never

@RunWith(JUnit4::class)
class LegacyTimeTableRepositoryTest {
    private val countryDao = mock(LegacyCountryDao::class.java)
    private val countryService = mock(LegacyApiService::class.java)
    private val repo = LegacyCountryRepository(InstantAppExecutors(), countryDao, countryService)

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

    var detailQuery = "{\n" +
            "  country(countryId: \"${123456}\") {\n" +
            "    id\n" +
            "    objectId\n" +
            "    name\n" +
            "    createdDate\n" +
            "    updatedDate\n" +
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
    fun loadCountry() {
        repo.loadCountry("123456")
        verify(countryDao).getCountryById("123456")
    }


    @Test
    fun loadCountryFromNetwork() {
        val dbData = MutableLiveData<Country>()
        Mockito.`when`(countryDao!!.getCountryById("123456")).thenReturn(dbData)
        val mockCountry1 = TestUtil.createCountry("Myanmar")

        val countries = mockCountry1
        val call = CountryResponse(data = Data(countries = Countries(data = listOf(TestUtil.createCountry("demo"))),
                days = Days(data = listOf(TestUtil.createTimtableDay(1, "1", "1"))), states = States(listOf(TestUtil.createState("123456"))),
                country = mockCountry1, state = TestUtil.createState("1"), day = TestUtil.createTimtableDay(1, "1", "1")))
        Mockito.`when`(countryService!!.getCountry(detailQuery)).thenReturn(ApiUtil.successCall(call))
        val observer = mock<Observer<Resource<Country>>>()
        repo.loadCountry("123456").observeForever(observer)
        verify(countryService, never()).getCountry(detailQuery)
        val updatedDbData = MutableLiveData<Country>()
        Mockito.`when`(countryDao!!.getCountryById("123456")).thenReturn(updatedDbData)
        dbData.value = null
        verify(countryService).getCountry(detailQuery)
    }

    @Test
    fun loadCountryOffline() {
        val dbData = MutableLiveData<Country>()
        val mockCountry1 = TestUtil.createCountry("Myanmar")
        val country: Country = mockCountry1

        dbData.value = country
        `when`(countryDao!!.getCountryById("123456")).thenReturn(dbData)
        val observer = mock<Observer<Resource<Country>>>()
        repo.loadCountry("123456").observeForever(observer)
        verify(countryService, never()).getCountryList(detailQuery)
        verify(observer).onChanged(Resource.success(country))
    }

    @Test
    fun loadCountryListFromNetwork() {
        val dbData = MutableLiveData<List<Country>>()
        Mockito.`when`(countryDao!!.getCountryList(100, offsetManager(100, 1))).thenReturn(dbData)
        val mockCountry1 = TestUtil.createCountry("Myanmar")
        val mockCountry2 = TestUtil.createCountry("Singapore")
        val mockCountry3 = TestUtil.createCountry("Malaysia")
        val countries: List<Country> = listOf<Country>(mockCountry1, mockCountry2, mockCountry3)
        val call = CountryResponse(data = Data(countries = Countries(data = countries),
                days = Days(data = listOf(TestUtil.createTimtableDay(1, "1", "1"))), states = States(listOf(TestUtil.createState("123456"))),
                country = TestUtil.createCountry("a"), state = TestUtil.createState("1"), day = TestUtil.createTimtableDay(1, "1", "1")))
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
    fun loadCountryListOffline() {
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
