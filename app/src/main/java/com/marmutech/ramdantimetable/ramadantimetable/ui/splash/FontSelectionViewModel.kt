package com.marmutech.ramdantimetable.ramadantimetable.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marmutech.ramdantimetable.ramadantimetable.domain.fonts.GetIsEnableUnicodeUseCase
import com.marmutech.ramdantimetable.ramadantimetable.domain.fonts.SetIsEnableUnicodeUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class FontSelectionViewModel @Inject constructor(
    private val setIsEnableUnicodeUseCase: SetIsEnableUnicodeUseCase,
    private val getIsEnableUnicodeUseCase: GetIsEnableUnicodeUseCase
) : ViewModel() {
    private val _isEnableUnicode = MutableLiveData<Boolean>()

    val isEnableUnicode: LiveData<Boolean> get() = _isEnableUnicode

    fun setEnableUnicode(enable: Boolean) {
        viewModelScope.launch {
            setIsEnableUnicodeUseCase.execute(enable).collect {}
        }
    }

    fun onViewCreated() {
        viewModelScope.launch {
            getIsEnableUnicodeUseCase.execute(Unit).collect {
                _isEnableUnicode.value = it
            }
        }
    }
}
