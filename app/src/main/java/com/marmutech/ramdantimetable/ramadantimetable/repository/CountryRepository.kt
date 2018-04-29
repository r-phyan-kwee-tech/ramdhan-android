package com.marmutech.ramdantimetable.ramadantimetable.repository

import android.arch.lifecycle.LiveData
import com.marmutech.ramdantimetable.ramadantimetable.AppExecutors
import com.marmutech.ramdantimetable.ramadantimetable.api.CountryService
import com.marmutech.ramdantimetable.ramadantimetable.db.CountryDao
import com.marmutech.ramdantimetable.ramadantimetable.db.offsetManager
import com.marmutech.ramdantimetable.ramadantimetable.model.Country
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
        return object : NetworkBoundResource<List<Country>, List<Country>>(appExecutors) {
            override fun shouldFetch(data: List<Country>?) = data == null

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

            override fun saveCallResult(item: List<Country>) {
                countryDao.bulkInsert(item)
            }

        }.asLiveData()
    }


}
