package com.marmutech.ramdantimetable.ramadantimetable.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marmutech.ramdantimetable.ramadantimetable.di.MainCoroutineDispatcher
import com.marmutech.ramdantimetable.ramadantimetable.domain.GetIsOnBoardingFinishUseCase
import com.marmutech.ramdantimetable.ramadantimetable.model.TimeTableDay
import com.marmutech.ramdantimetable.ramadantimetable.ui.ScreenType.ListScreen
import com.marmutech.ramdantimetable.ramadantimetable.ui.ScreenType.SplashScreen
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class MainViewModel @Inject constructor(
    @MainCoroutineDispatcher private val dispatcher: CoroutineDispatcher,
    private val getIsOnBoardingFinishUseCase: GetIsOnBoardingFinishUseCase
) : ViewModel() {

    private val _mainUiModel = MutableLiveData<MainUiModel>()
    val mainUiModel: LiveData<MainUiModel> get() = _mainUiModel

    fun onCreate() {
        Timber.d("onCreate")
        viewModelScope.launch(dispatcher) {
            _mainUiModel.value =
                MainUiModel(if (getIsOnBoardingFinishUseCase.execute(Unit)) ListScreen else SplashScreen)
        }
    }

    fun goTo(screenType: ScreenType) {
        Timber.d("screenType $screenType , mainUiModel ${mainUiModel.value}")
        viewModelScope.launch(dispatcher) {
            _mainUiModel.value =
                mainUiModel.value?.copy(
                    openScreen = screenType,
                )
        }
    }
}

data class MainUiModel(
    val openScreen: ScreenType,
)

sealed class ScreenType {
    object SplashScreen : ScreenType()
    object ListScreen : ScreenType()
    data class DetailScreen(val detailParam: TimeTableDay) : ScreenType()
    object CreditScreen : ScreenType()
    object LicenseScreen : ScreenType()
    object ChangeLocation : ScreenType()
}
