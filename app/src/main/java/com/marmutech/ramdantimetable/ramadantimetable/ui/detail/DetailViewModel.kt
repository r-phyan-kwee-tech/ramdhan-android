package com.marmutech.ramdantimetable.ramadantimetable.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.marmutech.ramdantimetable.ramadantimetable.model.TimeTableDay
import com.marmutech.ramdantimetable.ramadantimetable.repository.TimeTableDayRepository
import com.marmutech.ramdantimetable.ramadantimetable.util.AbsentLiveData
import com.marmutech.ramdantimetable.ramadantimetable.vo.Resource
import javax.inject.Inject


class DetailViewModel
@Inject constructor(timeTableDayRepository: TimeTableDayRepository) : ViewModel() {
    private val _dayId = MutableLiveData<String>()


    val timeTableDay: LiveData<Resource<TimeTableDay>> = Transformations.switchMap(_dayId) { dayId ->
        if (dayId == null) {
            AbsentLiveData.create()
        } else {
            timeTableDayRepository.loadTimetableDay(dayId)
        }
    }

    fun loadDay(dayId: String?) {
        if (_dayId.value != dayId) {
            _dayId.value = dayId
        }
    }


}

