package com.marmutech.ramdantimetable.ramadantimetable.api

import android.arch.lifecycle.LiveData
import com.marmutech.ramdantimetable.ramadantimetable.model.CountryResponse
import com.marmutech.ramdantimetable.ramadantimetable.model.DayResponse
import com.marmutech.ramdantimetable.ramadantimetable.model.StateResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CountryService {
    @GET("api")
    fun getCountryList(@Query("query") graphqlQuery: String): LiveData<ApiResponse<CountryResponse>>


    @GET("api")
    fun getCountry(@Query("query") graphqlQuery: String): LiveData<ApiResponse<CountryResponse>>

}

interface StateService {
    @GET("api")
    fun getStateList(@Query("query") graphqlQuery: String): LiveData<ApiResponse<StateResponse>>

    @GET("api")
    fun getState(@Query("query") graphqlQuery: String): LiveData<ApiResponse<StateResponse>>
}

interface TimeTableDayServie {
    @GET("api")
    fun getTimetableList(@Query("query") graphqlQuery: String): LiveData<ApiResponse<DayResponse>>

    @GET("api")
    fun getTimetable(@Query("query") graphqlQuery: String): LiveData<ApiResponse<DayResponse>>

}