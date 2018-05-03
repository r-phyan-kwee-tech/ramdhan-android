package com.marmutech.ramdantimetable.ramadantimetable.ui.schedule

import com.marmutech.ramdantimetable.ramadantimetable.model.TimeTableDay

interface ScheduleClickCallBack {
    fun onClick(timeTableDay: TimeTableDay)
}