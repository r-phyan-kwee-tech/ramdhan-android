package com.marmutech.ramdantimetable.ramadantimetable.ui.schedule

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.marmutech.ramdantimetable.ramadantimetable.model.TimeTableDay
import com.marmutech.ramdantimetable.ramadantimetable.repository.TimeTableDayRepository
import com.marmutech.ramdantimetable.ramadantimetable.vo.Resource

class ScheduleViewModel(application: Application, timeRepo: TimeTableDayRepository) : AndroidViewModel(application) {

    var daysList: LiveData<Resource<List<TimeTableDay>>>
        get() {
            return daysList
        }

    init {
        daysList = timeRepo.loadTimetableDayList()
    }


}