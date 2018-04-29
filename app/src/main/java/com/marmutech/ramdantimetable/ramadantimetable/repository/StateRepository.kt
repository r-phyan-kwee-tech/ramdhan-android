package com.marmutech.ramdantimetable.ramadantimetable.repository

import android.arch.lifecycle.LiveData
import com.marmutech.ramdantimetable.ramadantimetable.AppExecutors
import com.marmutech.ramdantimetable.ramadantimetable.api.StateService
import com.marmutech.ramdantimetable.ramadantimetable.db.StateDao
import com.marmutech.ramdantimetable.ramadantimetable.db.offsetManager
import com.marmutech.ramdantimetable.ramadantimetable.model.State
import com.marmutech.ramdantimetable.ramadantimetable.vo.Resource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StateRepository @Inject constructor(
        private val appExecutors: AppExecutors,
        private val stateDao: StateDao,
        private val stateSertice: StateService
) {
    fun loadStateList(countryId: String, limit: Int, page: Int): LiveData<Resource<List<State>>> {
        return object : NetworkBoundResource<List<State>, List<State>>(appExecutors) {
            override fun shouldFetch(data: List<State>?) = data == null

            var query = ""
            override fun createCall() = stateSertice.getStateList(query)

            override fun loadFromDb(): LiveData<List<State>> {

                return stateDao.getStateByCountryId(countryId, limit, offsetManager(limit, page))
            }

            override fun saveCallResult(item: List<State>) {
                stateDao.bulkInsert(item)
            }

        }.asLiveData()
    }


}
