package com.marmutech.ramdantimetable.ramadantimetable.ui.detail

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import com.marmutech.ramdantimetable.ramadantimetable.model.TimeTableDay
import com.marmutech.ramdantimetable.ramadantimetable.repository.TimeTableDayRepository
import com.marmutech.ramdantimetable.ramadantimetable.util.AbsentLiveData
import com.marmutech.ramdantimetable.ramadantimetable.vo.Resource
import javax.inject.Inject


class DetailViewModel
@Inject constructor(timeTableDayRepository: TimeTableDayRepository) : ViewModel() {
    private val _dayId = MutableLiveData<String>()


    val timeTableDay: LiveData<Resource<TimeTableDay>> = Transformations.switchMap(_dayId) { login ->
        if (login == null) {
            AbsentLiveData.create()
        } else {
            timeTableDayRepository.loadTimetableDay(login)
        }
    }

    fun loadDay(dayId: String?) {
        if (_dayId.value != dayId) {
            _dayId.value = dayId
        }
    }


}

