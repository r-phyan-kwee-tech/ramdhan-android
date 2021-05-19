package com.marmutech.ramdantimetable.ramadantimetable.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.marmutech.ramdantimetable.ramadantimetable.domain.fonts.GetIsEnableUnicode
import com.marmutech.ramdantimetable.ramadantimetable.domain.fonts.SetIsEnableUnicode
import javax.inject.Inject

class FontSelectionViewModel @Inject constructor(
    private val setIsEnableUnicode: SetIsEnableUnicode,
    private val getIsEnableUnicode: GetIsEnableUnicode
) : ViewModel() {
    private val _isEnableUnicode = MutableLiveData<Boolean>()

    val isEnableUnicode: LiveData<Boolean> get() = _isEnableUnicode

    fun setEnableUnicode(enable: Boolean) {

    }
}
