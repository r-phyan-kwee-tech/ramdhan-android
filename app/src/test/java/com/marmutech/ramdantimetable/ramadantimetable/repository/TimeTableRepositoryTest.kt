package com.marmutech.ramdantimetable.ramadantimetable.repository

import com.marmutech.ramdantimetable.ramadantimetable.api.ApiService
import com.marmutech.ramdantimetable.ramadantimetable.db.CountryDao
import com.marmutech.ramdantimetable.ramadantimetable.db.StateDao
import com.marmutech.ramdantimetable.ramadantimetable.util.UserPrefUtil
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.kotlin.any
import org.mockito.kotlin.only
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import retrofit2.Response

@RunWith(JUnit4::class)
class TimeTableRepositoryTest {

    private lateinit var repo: TimeTableRepo

    private val countryDao: CountryDao = mock { }
    private val apiService: ApiService = mock { }
    private val userPrefUtil: UserPrefUtil = mock { }
    private val stateDao: StateDao = mock { }

    @Before
    fun setUp() {
        repo = TimeTableRepositoryImpl(countryDao, apiService, userPrefUtil, stateDao)
    }

    @After
    fun tearDown() {

    }

    @ExperimentalCoroutinesApi
    @Test
    fun loadFromDbCountries() = runBlockingTest {
        whenever(
            countryDao.getCountryList(
                any(),
                any()
            )
        ).thenReturn(flowOf(FakeData.getCountryList()))
        val result = repo.loadCountryList().first()

        verify(countryDao, only()).getCountryList(any(), any())
        assertNotEquals(result.size, 0)
        assertEquals(result.size, 1)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun loadCountryFromApiAndInsertIntoDb() = runBlockingTest {
        //make db empty
        whenever(countryDao.getCountryList(any(), any())).thenReturn(flowOf(emptyList()))
        whenever(apiService.getCountryList(any())).thenReturn(Response.success(FakeData.getCountryResponse()))
        val result = repo.loadCountryList().first()
        assertEquals(result.size, 1)
        verify(countryDao, times(1)).bulkInsert(any())
    }

    //todo add cache test after cache separation

}
