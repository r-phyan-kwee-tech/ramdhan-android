package com.marmutech.ramdantimetable.ramadantimetable.ui.schedule

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marmutech.ramdantimetable.ramadantimetable.R
import com.marmutech.ramdantimetable.ramadantimetable.domain.days.GetDaysListUseCase
import com.marmutech.ramdantimetable.ramadantimetable.domain.state.GetSelectedStateNameUseCase
import com.marmutech.ramdantimetable.ramadantimetable.model.TimeTableDay
import com.marmutech.ramdantimetable.ramadantimetable.util.exceptionHandler
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class ScheduleViewModel @Inject constructor(
    private val getDaysListUseCase: GetDaysListUseCase,
    private val getSelectedStateNameUseCase: GetSelectedStateNameUseCase
) : ViewModel() {

    private val _uiModel = MutableLiveData(ScheduleUiModel.initial())
    val uiModel: LiveData<ScheduleUiModel> get() = _uiModel

    private val exceptionHandler = viewModelScope.exceptionHandler {
        Timber.d("exceptionHandler ${it.localizedMessage}")
        _uiModel.value = _uiModel.value?.copy(errorMessage = R.string.str_check_connection)
    }

    fun onViewCreated() {
        viewModelScope.launch(exceptionHandler) {
            getDaysListUseCase.execute(Unit).collect {
                _uiModel.value = mapScheduleUiModel(it, getSelectedStateNameUseCase.execute(Unit))
            }
        }
    }

    private fun mapScheduleUiModel(list: List<TimeTableDay>, stateName: String?): ScheduleUiModel {
        val isEid = list.isEmpty()
        return ScheduleUiModel(
            loading = false,
            isEid = isEid,
            days = list,
            toolBarTitle = stateName.orEmpty(),
            errorMessage = null
        )
    }

    data class ScheduleUiModel(
        val loading: Boolean,
        val days: List<TimeTableDay>? = null,
        val isEid: Boolean,
        val toolBarTitle: String = "",
        @StringRes val errorMessage: Int? = null
    ) {
        companion object {
            fun initial(): ScheduleUiModel {
                return ScheduleUiModel(loading = true, isEid = false)
            }
        }
    }
}
