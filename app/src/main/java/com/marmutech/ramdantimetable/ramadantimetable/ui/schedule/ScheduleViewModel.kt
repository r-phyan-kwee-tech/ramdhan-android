package com.marmutech.ramdantimetable.ramadantimetable.ui.schedule

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import com.marmutech.ramdantimetable.ramadantimetable.model.TimeTableDay
import com.marmutech.ramdantimetable.ramadantimetable.repository.TimeTableDayRepository
import com.marmutech.ramdantimetable.ramadantimetable.util.AbsentLiveData
import com.marmutech.ramdantimetable.ramadantimetable.util.UserPrefUtil
import com.marmutech.ramdantimetable.ramadantimetable.vo.Resource
import javax.inject.Inject

//class ScheduleViewModel(application: Application, timeRepo: TimeTableDayRepository) : AndroidViewModel(application) {
class ScheduleViewModel @Inject constructor(timeRepo: TimeTableDayRepository) : ViewModel() {

    private lateinit var userPrefUtil: UserPrefUtil
    private val _repoId = MutableLiveData<StateListParam>()


    var daysList: LiveData<Resource<List<TimeTableDay>>> = Transformations.switchMap(_repoId) { input ->
        input.ifExists { stateId, limit, page ->
            //userPrefUtil=application.getUserPref()
            timeRepo.loadTimetableDayList(stateId, limit, page)
        }
    }


//    init {
//
//        daysList = timeRepo.loadTimetableDayList(stateId = "c4e237869fc04b3e8cc7a79185a743b7", limit = 50, page = 1)
//    }

    /**
     * Load Time Table Day List from Repo
     */
    fun loadTimetableDayList(stateId: String?, limit: Int, page: Int) {
        val update = StateListParam(stateId, limit, page)
        if (_repoId.value == update) {
            return
        }
        _repoId.value = update
    }

    data class StateListParam(val stateId: String?, val limit: Int?, val page: Int?) {
        fun <T> ifExists(f: (String, Int, Int) -> LiveData<T>): LiveData<T> {
            return if (stateId.isNullOrBlank() || limit == null || page == null) {
                AbsentLiveData.create()
            } else {
                f(stateId!!, limit, page)
            }
        }
    }


}