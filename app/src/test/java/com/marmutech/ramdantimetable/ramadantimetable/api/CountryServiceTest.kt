package com.marmutech.ramdantimetable.ramadantimetable.api

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.marmutech.ramdantimetable.ramadantimetable.model.CountryResponse
import com.marmutech.ramdantimetable.ramadantimetable.util.LiveDataTestUtil.getValue
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.Okio
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.core.IsNull.notNullValue
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(JUnit4::class)
class CountryServiceTest {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var serviceLegacy: LegacyApiService

    private lateinit var mockWebServer: MockWebServer


    @Before
    fun createService() {
        mockWebServer = MockWebServer()
        serviceLegacy = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .build()
            .create(LegacyApiService::class.java)
    }

    @After
    fun stopService() {
        mockWebServer.shutdown()
    }

    @Test
    fun getCountry() {
        enqueueResponse("country.json")
        val yigit = (getValue(serviceLegacy.getCountry("")) as ApiSuccessResponse).body

        val request = mockWebServer.takeRequest()
        assertThat(request.path, `is`("/api?query="))

        assertThat<CountryResponse>(yigit, notNullValue())
    }

    @Test
    fun getCountryList() {
        enqueueResponse("country-list.json")
        val yigit = (getValue(serviceLegacy.getCountryList("")) as ApiSuccessResponse).body

        val request = mockWebServer.takeRequest()
        assertThat(request.path, `is`("/api?query="))

        assertThat<CountryResponse>(yigit, notNullValue())
    }


    private fun enqueueResponse(fileName: String, headers: Map<String, String> = emptyMap()) {
        val inputStream = javaClass.classLoader
                .getResourceAsStream("$fileName")
        val source = Okio.buffer(Okio.source(inputStream))
        val mockResponse = MockResponse()
        for ((key, value) in headers) {
            mockResponse.addHeader(key, value)
        }
        mockWebServer.enqueue(
                mockResponse
                        .setBody(source.readString(Charsets.UTF_8))
        )
    }
}
