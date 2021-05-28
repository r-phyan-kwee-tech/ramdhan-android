package com.marmutech.ramdantimetable.ramadantimetable.repository

import com.marmutech.ramdantimetable.ramadantimetable.api.ApiService
import com.marmutech.ramdantimetable.ramadantimetable.db.CountryDao
import com.marmutech.ramdantimetable.ramadantimetable.db.StateDao
import com.marmutech.ramdantimetable.ramadantimetable.model.Country
import com.marmutech.ramdantimetable.ramadantimetable.model.CountryResponse
import com.marmutech.ramdantimetable.ramadantimetable.model.State
import com.marmutech.ramdantimetable.ramadantimetable.model.StateResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

//todo add sorting
//todo add compact font type
class TimeTableRepositoryImpl @Inject constructor(
    private val countryDao: CountryDao,
    private val apiService: ApiService,
    private val userSettingRepository: UserSettingRepository,
    private val stateDao: StateDao
) : TimeTableRepo {

    override suspend fun loadCountryList(): Flow<List<Country>> = flow {

        val countriesFromDb = countryDao.getCountryList().first()
        if (countriesFromDb.isEmpty()) {
            val apiCountries = getCountryListFromApi()
            apiCountries?.let {
                countryDao.bulkInsert(apiCountries)
                emit(apiCountries)
            }
        } else {
            emit(countriesFromDb)
        }
    }

    override suspend fun loadStateList(countryId: String): Flow<List<State>> = flow {

        val stateFromDb = stateDao.getStateByCountryId(countryId).first()
        if (stateFromDb.isEmpty()) {
            val apiCountries = getStateListFromApi(countryId)
            apiCountries?.let {
                stateDao.bulkInsert(apiCountries)
                emit(apiCountries)
            }
        } else {
            emit(stateFromDb)
        }

    }

    private suspend fun loadStateListWithCacheProcess(countryId: String) = flow {
        stateDao.getStateByCountryId(countryId)
            .map {
                if (it.isEmpty()) getStateListFromApi(countryId) else it
            }.collect {
                it?.let {
                    //stateDao.bulkInsert(it)
                    emit(it)
                }
            }
    }

    private suspend fun getStateListFromApi(countryId: String): List<State>? {
        val respondBody = apiService.getStateList(stateListQuery(countryId)).body()
        return mapStateResponseToStates(respondBody)
    }

    private suspend fun getCountryListFromApi(): List<Country>? {
        val respondBody = apiService.getCountryList(countryListQuery()).body()
        return mapCountryResponseToCountries(respondBody)
    }

    private fun mapCountryResponseToCountries(countryResponse: CountryResponse?) = countryResponse?.data?.countries?.data

    private fun mapStateResponseToStates(stateResponse: StateResponse?) = stateResponse?.data?.states?.data


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
}
