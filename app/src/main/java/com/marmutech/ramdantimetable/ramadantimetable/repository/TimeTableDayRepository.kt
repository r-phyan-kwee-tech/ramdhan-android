package com.marmutech.ramdantimetable.ramadantimetable.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.marmutech.ramdantimetable.ramadantimetable.AppExecutors
import com.marmutech.ramdantimetable.ramadantimetable.api.TimeTableDayServie
import com.marmutech.ramdantimetable.ramadantimetable.db.TimeTableDao
import com.marmutech.ramdantimetable.ramadantimetable.db.offsetManager
import com.marmutech.ramdantimetable.ramadantimetable.model.*
import com.marmutech.ramdantimetable.ramadantimetable.vo.Resource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TimeTableDayRepository @Inject constructor(
        private val appExecutors: AppExecutors,
        private val timttableDao: TimeTableDao,
        private val countryService: TimeTableDayServie
) {
    fun loadTimetableDayList(stateId: String, limit: Int, page: Int): LiveData<Resource<DayResponse>> {
        return object : NetworkBoundResource<DayResponse, DayResponse>(appExecutors) {

            override fun shouldFetch(data: DayResponse?) = data == null

            var query = "{\n" +
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
                    "}"

            override fun createCall() = countryService.getTimetableList(query)


            override fun loadFromDb(): LiveData<DayResponse> {

                return MutableLiveData<DayResponse>().apply {
                    value = DayResponse(data = Data(
                            days = Days(data = timttableDao.getDayByStateId(stateId, limit, offsetManager(limit, page)).value!!),
                            countries = Countries(data = emptyList()),
                            states = States(data = emptyList())
                    ))
                }
            }

            override fun saveCallResult(item: DayResponse) {
                timttableDao.bulkInsert(item.data.days.data)
            }
        }.asLiveData()
    }


}
