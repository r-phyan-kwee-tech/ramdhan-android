package com.marmutech.ramdantimetable.ramadantimetable.api

import android.arch.lifecycle.LiveData
import com.marmutech.ramdantimetable.ramadantimetable.model.Country
import com.marmutech.ramdantimetable.ramadantimetable.model.State
import com.marmutech.ramdantimetable.ramadantimetable.model.TimeTableDay
import retrofit2.http.GET
import retrofit2.http.Query

interface CountryService {
    @GET("api")
    fun getCountryList(@Query("query") graphqlQuery: String): LiveData<List<Country>>

}

interface StateService {
    @GET("api")
    fun getStateList(@Query("query") graphqlQuery: String): LiveData<List<State>>
}

interface TimeTableDayServie {
    @GET("api")
    fun getTimetableList(@Query("query") graphqlQuery: String): LiveData<List<TimeTableDay>>
}