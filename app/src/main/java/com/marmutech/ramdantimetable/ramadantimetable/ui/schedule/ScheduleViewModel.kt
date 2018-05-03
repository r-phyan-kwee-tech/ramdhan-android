package com.marmutech.ramdantimetable.ramadantimetable.ui.schedule

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.marmutech.ramdantimetable.ramadantimetable.model.TimeTableDay
import com.marmutech.ramdantimetable.ramadantimetable.repository.TimeTableDayRepository
import com.marmutech.ramdantimetable.ramadantimetable.util.UserPrefUtil
import com.marmutech.ramdantimetable.ramadantimetable.vo.Resource

class ScheduleViewModel(application: Application, timeRepo: TimeTableDayRepository) : AndroidViewModel(application) {

    private lateinit var userPrefUtil: UserPrefUtil
    var daysList: LiveData<Resource<List<TimeTableDay>>>
        get() {
            return daysList
        }

    init {
        userPrefUtil=application.
        daysList = timeRepo.loadTimetableDayList(stateId = "c4e237869fc04b3e8cc7a79185a743b7",limit = 50,page = 1)
    }


}