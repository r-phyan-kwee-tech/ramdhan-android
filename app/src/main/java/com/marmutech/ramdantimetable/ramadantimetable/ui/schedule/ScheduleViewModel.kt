package com.marmutech.ramdantimetable.ramadantimetable.ui.schedule

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marmutech.ramdantimetable.ramadantimetable.domain.days.GetDaysListUseCase
import com.marmutech.ramdantimetable.ramadantimetable.model.TimeTableDay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class ScheduleViewModel @Inject constructor(
    private val getDaysListUseCase: GetDaysListUseCase
) :
    ViewModel() {

    private val _uiModel = MutableLiveData<ScheduleUiModel>()
    val uiModel: LiveData<ScheduleUiModel> get() = _uiModel

    fun onViewCreated() {
        viewModelScope.launch {
            getDaysListUseCase.execute(Unit).collect {
                _uiModel.value = mapTimeTableDayList(it)
            }
        }
    }

    private fun mapTimeTableDayList(list: List<TimeTableDay>): ScheduleUiModel {
        val isEid = list.isEmpty()
        return ScheduleUiModel(
            loading = false,
            isEid = isEid,
            days = list
        )
    }

    data class ScheduleUiModel(
        val loading: Boolean,
        val days: List<TimeTableDay>? = null,
        val isEid: Boolean
    )
}
