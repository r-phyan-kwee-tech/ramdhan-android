package com.marmutech.ramdantimetable.ramadantimetable.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.marmutech.ramdantimetable.ramadantimetable.domain.GetIsOnBoardingFinishUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getIsOnBoardingFinishUseCase: GetIsOnBoardingFinishUseCase
) : ViewModel() {

    private val _mainUiModel = MutableStateFlow<MainUiModel?>(null)
    val mainUiModel: LiveData<MainUiModel?> get() = _mainUiModel.asLiveData()

    fun onCreate() {
        viewModelScope.launch {
            _mainUiModel.value = MainUiModel(if (getIsOnBoardingFinishUseCase.execute(Unit)) ScreenType.ListScreen else ScreenType.SplashScreen)
        }
    }
}

data class MainUiModel(
    val openScreen: ScreenType
)

enum class ScreenType {
    SplashScreen, ListScreen, Detail, Screen, SettingScreen
}
