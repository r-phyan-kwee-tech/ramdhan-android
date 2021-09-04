package com.marmutech.ramdantimetable.ramadantimetable.repository

import androidx.lifecycle.LiveData
import com.marmutech.ramdantimetable.ramadantimetable.AppExecutors
import com.marmutech.ramdantimetable.ramadantimetable.api.ApiResponse
import com.marmutech.ramdantimetable.ramadantimetable.api.LegacyApiService
import com.marmutech.ramdantimetable.ramadantimetable.db.TimeTableDao
import com.marmutech.ramdantimetable.ramadantimetable.db.offsetManager
import com.marmutech.ramdantimetable.ramadantimetable.model.DayResponse
import com.marmutech.ramdantimetable.ramadantimetable.model.TimeTableDay
import com.marmutech.ramdantimetable.ramadantimetable.vo.Resource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LegacyTimeTableDayRepository @Inject constructor(
    private val appExecutors: AppExecutors,
    private val timttableDao: TimeTableDao,
    private val legacyApiService: LegacyApiService
) {
    fun loadTimetableDayList(
        stateId: String,
        limit: Int,
        page: Int
    ): LiveData<Resource<List<TimeTableDay>>> {
        return object : NetworkBoundResource<List<TimeTableDay>, DayResponse>(appExecutors) {

            override fun shouldFetch(data: List<TimeTableDay>?): Boolean =
                data == null || data.isEmpty()

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

            override fun createCall(): LiveData<ApiResponse<DayResponse>> {
                return legacyApiService.getTimetableList(query)
            }


            override fun loadFromDb(): LiveData<List<TimeTableDay>> {
                return timttableDao.getDayByStateId(stateId = stateId, limit = limit, offset = offsetManager(limit, page))
            }

            override fun saveCallResult(item: DayResponse) {
                println("RESULT SAVED----->")
                timttableDao.bulkInsert(item.data.days!!.data)
            }
        }.asLiveData()
    }

    fun loadTimetableDay(dayId: String): LiveData<Resource<TimeTableDay>> {
        return object : NetworkBoundResource<TimeTableDay, DayResponse>(appExecutors) {
            override fun shouldFetch(data: TimeTableDay?): Boolean = data == null

            var query = "{\n" +
                    "  day(dayId: \"$dayId\") {\n" +
                    "    id\n" +
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
                    "  }\n" +
                    "}"

            override fun createCall(): LiveData<ApiResponse<DayResponse>> {
                return legacyApiService.getTimetable(query)
            }

            override fun loadFromDb(): LiveData<TimeTableDay> {

                return timttableDao.getDayById(dayId)

            }

            override fun saveCallResult(item: DayResponse) {
                timttableDao.insert(item.data.day!!)
            }


        }.asLiveData()
    }

}
