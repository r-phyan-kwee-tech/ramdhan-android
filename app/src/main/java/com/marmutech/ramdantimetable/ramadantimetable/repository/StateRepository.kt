package com.marmutech.ramdantimetable.ramadantimetable.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.marmutech.ramdantimetable.ramadantimetable.AppExecutors
import com.marmutech.ramdantimetable.ramadantimetable.api.StateService
import com.marmutech.ramdantimetable.ramadantimetable.db.StateDao
import com.marmutech.ramdantimetable.ramadantimetable.db.offsetManager
import com.marmutech.ramdantimetable.ramadantimetable.model.*
import com.marmutech.ramdantimetable.ramadantimetable.vo.Resource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StateRepository @Inject constructor(
        private val appExecutors: AppExecutors,
        private val stateDao: StateDao,
        private val stateSertice: StateService
) {
    fun loadStateList(countryId: String, limit: Int, page: Int): LiveData<Resource<StateResponse>> {
        return object : NetworkBoundResource<StateResponse, StateResponse>(appExecutors) {
            override fun shouldFetch(data: StateResponse?) = data == null

            var query = "{\n" +
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

            override fun createCall() = stateSertice.getStateList(query)

            override fun loadFromDb(): LiveData<StateResponse> {

                println("AAAA")
                println(stateDao)
                println("AAAA")
                return MutableLiveData<StateResponse>().apply {
                    value = StateResponse(data = Data(
                            days = Days(data = emptyList()),
                            countries = Countries(data = emptyList()),
                            states = States(data = stateDao.getStateByCountryId(countryId, limit, offsetManager(limit, page)).value!!)
                    ))
                }

            }

            override fun saveCallResult(item: StateResponse) {
                stateDao.bulkInsert(item.data.states.data)
            }


        }.asLiveData()
    }


}
