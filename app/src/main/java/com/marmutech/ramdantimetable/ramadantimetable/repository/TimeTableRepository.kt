package com.marmutech.ramdantimetable.ramadantimetable.repository

import com.marmutech.ramdantimetable.ramadantimetable.api.ApiService
import com.marmutech.ramdantimetable.ramadantimetable.db.CountryDao
import com.marmutech.ramdantimetable.ramadantimetable.db.StateDao
import com.marmutech.ramdantimetable.ramadantimetable.db.TimeTableDao
import com.marmutech.ramdantimetable.ramadantimetable.model.*
import com.marmutech.ramdantimetable.ramadantimetable.util.NetworkBoundResource
import kotlinx.coroutines.flow.*
import retrofit2.Response
import javax.inject.Inject

//todo add sorting
//todo add compact font type
class TimeTableRepositoryImpl @Inject constructor(
    private val countryDao: CountryDao,
    private val apiService: ApiService,
    private val userSettingRepository: UserSettingRepository,
    private val stateDao: StateDao,
    private val timeTableDao: TimeTableDao
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

    override suspend fun loadDays(): Flow<List<TimeTableDay>> {
        //todo if null user have to choose again
        val userSelectedStateId = userSettingRepository.getSelectedStateId()!!
        return object : NetworkBoundResource<DayResponse, TimeTableDay, List<TimeTableDay>>() {
            override suspend fun apiCallFlow(): Response<DayResponse> {
                return apiService.getDayList(dayListQuery(userSelectedStateId))
            }

            override fun dbQueryFlow(): Flow<List<TimeTableDay>> =
                timeTableDao.getDayByStateId(userSelectedStateId)

            override fun dbInsertCall(data: List<TimeTableDay>) = timeTableDao.bulkInsert(data)

            override fun mapDataFromApiToDb(apiData: DayResponse): List<TimeTableDay> =
                apiData.data.days!!.data

            override fun mapDBDataFromUiData(dbData: List<TimeTableDay>): List<TimeTableDay> =
                dbData
        }.execute()
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

    private fun mapCountryResponseToCountries(countryResponse: CountryResponse?) =
        countryResponse?.data?.countries?.data

    private fun mapStateResponseToStates(stateResponse: StateResponse?) =
        stateResponse?.data?.states?.data


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

        private fun dayListQuery(stateId: String) = "{\n" +
                "  days(limit: $limit, page: $page, stateId: \"$stateId\") {\n" +
                "    data {\n" +
                "      id\n" +
                "    objectId\n" +
                "    day\n" +
                "    dayMm\n" +
                "    calendarDay\n" +
                "    hijariDay\n" +
                "    sehriTime\n" +
                "    iftariTime\n" +
                "    sehriTimeDesc\n" +
                "    iftariTimeDesc\n" +
                "    sehriTimeDescMmUni\n" +
                "    sehriTimeDescMmZawgyi\n" +
                "    iftariTimeDescMmZawgyi\n" +
                "    iftariTimeDescMmUni\n" +
                "    isKadir\n" +
                "    duaAr\n" +
                "    duaEn\n" +
                "    duaMmUni\n" +
                "    duaMmZawgyi\n" +
                "    countryId\n" +
                "    stateId\n" +
                "    createdDate\n" +
                "    updatedDate\n" +
                "    }\n" +
                "  }\n" +
                "}\n"

    }
}

interface TimeTableRepo {
    suspend fun loadCountryList(): Flow<List<Country>>
    suspend fun loadStateList(countryId: String): Flow<List<State>>
    suspend fun loadDays(): Flow<List<TimeTableDay>>
}
