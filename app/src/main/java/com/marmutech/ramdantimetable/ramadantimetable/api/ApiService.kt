package com.marmutech.ramdantimetable.ramadantimetable.api

import com.marmutech.ramdantimetable.ramadantimetable.model.CountryResponse
import com.marmutech.ramdantimetable.ramadantimetable.model.DayResponse
import com.marmutech.ramdantimetable.ramadantimetable.model.StateResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("api")
    suspend fun getCountryList(@Query("query") graphqlQuery: String): Response<CountryResponse>

    @GET("api")
    suspend fun getStateList(@Query("query") graphqlQuery: String): Response<StateResponse>

    @GET("api")
    suspend fun getDayList(@Query("query") graphqlQuery: String): Response<DayResponse>
}
