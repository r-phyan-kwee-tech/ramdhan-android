package com.marmutech.ramdantimetable.ramadantimetable

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.marmutech.ramdantimetable.ramadantimetable.api.ApiResponse
import retrofit2.Response

object ApiUtil {
    fun <T : Any> successCall(data: T) = createCall(Response.success(data))

    fun <T : Any> createCall(response: Response<T>) = MutableLiveData<ApiResponse<T>>().apply {
        value = ApiResponse.create(response)
    } as LiveData<ApiResponse<T>>

    fun <T : Any> toLiveData(data: T) = MutableLiveData<T>().apply {
        value = data
    } as LiveData<T>
}