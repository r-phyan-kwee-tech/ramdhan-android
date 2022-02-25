package com.marmutech.ramdantimetable.ramadantimetable.util

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.transform
import retrofit2.Response

abstract class NetworkBoundResource<T, R, S> {
    abstract suspend fun apiCallFlow(): Response<T>
    abstract fun dbQueryFlow(): Flow<List<R>>
    abstract fun dbInsertCall(data: List<R>)
    abstract fun mapDataFromApiToDb(apiData: T): List<R>
    abstract fun mapDBDataFromUiData(dbData: List<R>): S

    suspend fun execute(): Flow<S> {
        return dbQueryFlow().transform { value ->
            if (value.isEmpty()) emit(chockApi())
            else emit(value)
        }.map {
            mapDBDataFromUiData(it)
        }
    }

    private suspend fun chockApi(): List<R> {
        val apiResult = apiCallFlow()
        if (apiResult.isSuccessful) {
            requireNotNull(apiResult.body())
            val dbData = mapDataFromApiToDb(apiResult.body()!!)
            dbInsertCall(dbData)
            return dbData
        } else error(apiResult.message())
    }
}
