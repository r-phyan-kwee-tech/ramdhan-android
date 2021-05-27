package com.marmutech.ramdantimetable.ramadantimetable.repository

import com.marmutech.ramdantimetable.ramadantimetable.api.ApiService
import com.marmutech.ramdantimetable.ramadantimetable.db.CountryDao
import com.marmutech.ramdantimetable.ramadantimetable.db.StateDao
import com.marmutech.ramdantimetable.ramadantimetable.db.offsetManager
import com.marmutech.ramdantimetable.ramadantimetable.model.Country
import com.marmutech.ramdantimetable.ramadantimetable.model.CountryResponse
import com.marmutech.ramdantimetable.ramadantimetable.model.State
import com.marmutech.ramdantimetable.ramadantimetable.model.StateResponse
import com.marmutech.ramdantimetable.ramadantimetable.util.UserPrefUtil
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

class TimeTableRepositoryImpl @Inject constructor(
    private val countryDao: CountryDao,
    private val apiService: ApiService,
    private val userPrefUtil: UserPrefUtil,
    private val stateDao: StateDao
) : TimeTableRepo {

    private var _cacheCountryList: List<Country>? = null
    private val cacheCountryList get() = _cacheCountryList!!

    private var _cacheStateList: List<State>? = null
    private val cacheStateList get() = _cacheStateList!!

    private var _cacheCountryId: String = userPrefUtil.getLocationId()

    override suspend fun loadCountryList(): Flow<List<Country>> = flow {
        if (_cacheCountryList != null) emit(cacheCountryList) else {
            //get from db
            countryDao.getCountryList(limit, offsetManager(limit, page)).collect {
                if (it.isNotEmpty()) {
                    _cacheCountryList = it
                    emit(cacheCountryList)
                } else {
                    val respondBody = apiService.getCountryList(countryListQuery())
                        .body()
                    if (respondBody == null) emit(emptyList<Country>()) else {
                        _cacheCountryList = mapCountryResponseToCountries(respondBody)
                        countryDao.bulkInsert(cacheCountryList)
                        emit(cacheCountryList)
                    }
                }
            }
        }
        Timber.d("end:loadCountryList")
    }

    override suspend fun loadStateList(countryId: String): Flow<List<State>> = flow {
        Timber.d("loadStateList start")
        Timber.d("_cacheCountryId $_cacheCountryId")
        if (_cacheCountryId == countryId) {
            if (_cacheStateList != null) {
                Timber.d("emit cacheStateList")
                emit(cacheStateList)
            } else {
                Timber.d("in first else")
                loadStateListWithCacheProcess(countryId)
            }
        } else {
            Timber.d("in else")
            loadStateListWithCacheProcess(countryId)
        }
    }

    override suspend fun loadNetWorkCountry(): Flow<List<Country>> = flow {
        val respond = apiService.getCountryList(countryListQuery()).body()!!
        Timber.d("country response $respond")
        emit(mapCountryResponseToCountries(respond)!!)
    }

    override suspend fun loadNetWorkState(countryId: String): Flow<List<State>> = flow {
        val respond = apiService.getStateList(stateListQuery(countryId)).body()!!
        emit(mapStateResponseToStates(respond)!!)
    }

    private suspend fun loadStateListWithCacheProcess(countryId: String) = flow {
        Timber.d("loadStateListWithCacheProcess")
        stateDao.getStateByCountryId(countryId).collect {
            if (it.isNotEmpty()) {
                _cacheStateList = it
                emit(cacheStateList)
            } else {
                val respondBody = apiService.getStateList(stateListQuery(countryId))
                    .body()
                if (respondBody == null) emit(emptyList<State>()) else {
                    _cacheStateList = mapStateResponseToStates(respondBody)
                    stateDao.bulkInsert(cacheStateList)
                    emit(cacheStateList)
                }
            }
        }
    }

    private fun mapCountryResponseToCountries(countryResponse: CountryResponse) = countryResponse.data.countries?.data

    private fun mapStateResponseToStates(stateResponse: StateResponse) = stateResponse.data.states?.data


    companion object {
        private const val limit = 500
        private const val page = 1

        private fun countryListQuery() = "{\n" +
            "  countries(limit: $limit, page: $page) {\n" +
            "    data {\n" +
            "      id\n" +
            "      objectId\n" +
            "      name\n" +
            "      createdDate\n" +
            "      updatedDate\n" +
            "    }\n" +
            "  }\n" +
            "}"

        private fun stateListQuery(countryId: String) = "{\n" +
            "  states(limit: $limit, page: $page, countryId: \"$countryId\") {\n" +
            "    data {\n" +
            "      id\n" +
            "      objectId\n" +
            "      nameMmUni\n" +
            "      nameMmZawgyi\n" +
            "      countryId\n" +
            "      createdDate\n" +
            "      updatedDate\n" +
            "    }\n" +
            "  }\n" +
            "}"

    }
}

interface TimeTableRepo {
    suspend fun loadCountryList(): Flow<List<Country>>
    suspend fun loadStateList(countryId: String): Flow<List<State>>

    suspend fun loadNetWorkCountry(): Flow<List<Country>>
    suspend fun loadNetWorkState(countryId: String): Flow<List<State>>
}
