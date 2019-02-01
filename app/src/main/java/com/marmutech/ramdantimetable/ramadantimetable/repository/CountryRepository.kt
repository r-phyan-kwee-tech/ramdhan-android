package com.marmutech.ramdantimetable.ramadantimetable.repository

import androidx.lifecycle.LiveData
import com.marmutech.ramdantimetable.ramadantimetable.AppExecutors
import com.marmutech.ramdantimetable.ramadantimetable.api.CountryService
import com.marmutech.ramdantimetable.ramadantimetable.db.CountryDao
import com.marmutech.ramdantimetable.ramadantimetable.db.offsetManager
import com.marmutech.ramdantimetable.ramadantimetable.model.Country
import com.marmutech.ramdantimetable.ramadantimetable.model.CountryResponse
import com.marmutech.ramdantimetable.ramadantimetable.vo.Resource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CountryRepository @Inject constructor(
        private val appExecutors: AppExecutors,
        private val countryDao: CountryDao,
        private val countryService: CountryService
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

            override fun createCall() = countryService.getCountryList(query)

            override fun loadFromDb(): LiveData<List<Country>> {
                return countryDao.getCountryList(limit, offsetManager(limit, page))

            }

            override fun saveCallResult(item: CountryResponse) {
                countryDao.bulkInsert(item.data.countries.data)
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

            override fun createCall() = countryService.getCountry(query)

            override fun loadFromDb(): LiveData<Country> {
                return countryDao.getCountryById(countryId)

            }

            override fun saveCallResult(item: CountryResponse) {
                countryDao.insert(item.data.country)
            }

        }.asLiveData()
    }


}
