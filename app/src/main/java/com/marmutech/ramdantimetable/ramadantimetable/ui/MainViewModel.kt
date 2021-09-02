package com.marmutech.ramdantimetable.ramadantimetable.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marmutech.ramdantimetable.ramadantimetable.domain.GetIsOnBoardingFinishUseCase
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getIsOnBoardingFinishUseCase: GetIsOnBoardingFinishUseCase
) : ViewModel() {

    private val _mainUiModel = MutableLiveData<MainUiModel>()
    val mainUiModel: LiveData<MainUiModel> get() = _mainUiModel

    fun onCreate() {
        Timber.d("onCreate")
        viewModelScope.launch {
            _mainUiModel.value =
                MainUiModel(if (getIsOnBoardingFinishUseCase.execute(Unit)) ScreenType.ListScreen else ScreenType.SplashScreen)
        }
    }

    fun goTo(screenType: ScreenType) {
        Timber.d("screenType $screenType , mainUiModel ${mainUiModel.value}")
        viewModelScope.launch {
            _mainUiModel.value = mainUiModel.value!!.copy(openScreen = ScreenType.ListScreen)
        }
    }
}

data class MainUiModel(
    val openScreen: ScreenType
)

enum class ScreenType {
    SplashScreen, ListScreen, Detail, Screen, SettingScreen
}
