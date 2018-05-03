package com.marmutech.ramdantimetable.ramadantimetable.ui.schedule

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.arch.paging.PagedList
import com.marmutech.ramdantimetable.ramadantimetable.model.TimeTableDay

class ScheduleViewModel : ViewModel() {
    val daysList: LiveData<List<TimeTableDay>>
    get() {
        return daysList
    }

    init {
        daysList=
    }




}