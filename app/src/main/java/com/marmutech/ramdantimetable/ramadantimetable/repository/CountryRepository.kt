package com.marmutech.ramdantimetable.ramadantimetable.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.marmutech.ramdantimetable.ramadantimetable.AppExecutors
import com.marmutech.ramdantimetable.ramadantimetable.api.CountryService
import com.marmutech.ramdantimetable.ramadantimetable.db.CountryDao
import com.marmutech.ramdantimetable.ramadantimetable.db.offsetManager
import com.marmutech.ramdantimetable.ramadantimetable.model.*
import com.marmutech.ramdantimetable.ramadantimetable.vo.Resource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CountryRepository @Inject constructor(
        private val appExecutors: AppExecutors,
        private val countryDao: CountryDao,
        private val countryService: CountryService
) {
    fun loadCountryList(limit: Int, page: Int): LiveData<Resource<CountryResponse>> {
        return object : NetworkBoundResource<CountryResponse, CountryResponse>(appExecutors) {
            override fun shouldFetch(data: CountryResponse?) = data == null
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

            override fun loadFromDb(): LiveData<CountryResponse> {
                return MutableLiveData<CountryResponse>().apply {
                    value = CountryResponse(data = Data(
                            days = Days(data = emptyList()),
                            countries = Countries(data = countryDao.getCountryList(limit, offsetManager(limit, page)).value!!),
                            states = States(data = emptyList())
                    ))
                }

            }

            override fun saveCallResult(item: CountryResponse) {
                countryDao.bulkInsert(item.data.countries.data)
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        }.asLiveData()
    }


}
