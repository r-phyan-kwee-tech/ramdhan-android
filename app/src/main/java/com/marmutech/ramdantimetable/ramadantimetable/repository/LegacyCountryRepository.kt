package com.marmutech.ramdantimetable.ramadantimetable.repository

import androidx.lifecycle.LiveData
import com.marmutech.ramdantimetable.ramadantimetable.AppExecutors
import com.marmutech.ramdantimetable.ramadantimetable.api.LegacyApiService
import com.marmutech.ramdantimetable.ramadantimetable.db.LegacyCountryDao
import com.marmutech.ramdantimetable.ramadantimetable.db.offsetManager
import com.marmutech.ramdantimetable.ramadantimetable.model.Country
import com.marmutech.ramdantimetable.ramadantimetable.model.CountryResponse
import com.marmutech.ramdantimetable.ramadantimetable.vo.Resource
import javax.inject.Inject
import javax.inject.Singleton

//todo use interface
@Singleton
class LegacyCountryRepository @Inject constructor(
    private val appExecutors: AppExecutors,
    private val legacyCountryDao: LegacyCountryDao,
    private val legacyApiService: LegacyApiService
) {
    fun loadCountryList(limit: Int, page: Int): LiveData<Resource<List<Country>>> {
        return object : NetworkBoundResource<List<Country>, CountryResponse>(appExecutors) {
            override fun shouldFetch(data: List<Country>?): Boolean = data == null || data.isEmpty()
            var query = "{\n" +
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

            override fun createCall() = legacyApiService.getCountryList(query)

            override fun loadFromDb(): LiveData<List<Country>> {
                return legacyCountryDao.getCountryList(limit, offsetManager(limit, page))

            }

            override fun saveCallResult(item: CountryResponse) {
                legacyCountryDao.bulkInsert(item.data.countries.data)
            }

        }.asLiveData()
    }


    fun loadCountry(countryId: String): LiveData<Resource<Country>> {
        return object : NetworkBoundResource<Country, CountryResponse>(appExecutors) {
            override fun shouldFetch(data: Country?): Boolean = data == null
            var query = "{\n" +
                "  country(countryId: \"$countryId\") {\n" +
                "    id\n" +
                "    objectId\n" +
                "    name\n" +
                "    createdDate\n" +
                "    updatedDate\n" +
                "  }\n" +
                "}"

            override fun createCall() = legacyApiService.getCountry(query)

            override fun loadFromDb(): LiveData<Country> {
                return legacyCountryDao.getCountryById(countryId)

            }

            override fun saveCallResult(item: CountryResponse) {
                legacyCountryDao.insert(item.data.country)
            }

        }.asLiveData()
    }


}
