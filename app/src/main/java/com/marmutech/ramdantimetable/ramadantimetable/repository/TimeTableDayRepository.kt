package com.marmutech.ramdantimetable.ramadantimetable.repository

import android.arch.lifecycle.LiveData
import com.marmutech.ramdantimetable.ramadantimetable.AppExecutors
import com.marmutech.ramdantimetable.ramadantimetable.api.TimeTableDayServie
import com.marmutech.ramdantimetable.ramadantimetable.db.TimeTableDao
import com.marmutech.ramdantimetable.ramadantimetable.db.offsetManager
import com.marmutech.ramdantimetable.ramadantimetable.model.TimeTableDay
import com.marmutech.ramdantimetable.ramadantimetable.vo.Resource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TimeTableDayRepository @Inject constructor(
        private val appExecutors: AppExecutors,
        private val timttableDao: TimeTableDao,
        private val countryService: TimeTableDayServie
) {
    fun loadTimetableDayList(stateId: String, limit: Int, page: Int): LiveData<Resource<List<TimeTableDay>>> {
        return object : NetworkBoundResource<List<TimeTableDay>, List<TimeTableDay>>(appExecutors) {
            override fun shouldFetch(data: List<TimeTableDay>?) = data == null

            var query = ""
            override fun createCall() = countryService.getTimetableList(query)

            override fun loadFromDb(): LiveData<List<TimeTableDay>> {

                return timttableDao.getDayByStateId(stateId, limit, offsetManager(limit, page))
            }

            override fun saveCallResult(item: List<TimeTableDay>) {
                timttableDao.bulkInsert(item)
            }

        }.asLiveData()
    }


}
