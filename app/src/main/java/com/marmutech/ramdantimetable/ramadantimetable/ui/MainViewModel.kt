package com.marmutech.ramdantimetable.ramadantimetable.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class MainViewModel @Inject constructor() : ViewModel() {

    private val _mainUiModel = MutableStateFlow<MainUiModel?>(null)
    val mainUiModel: LiveData<MainUiModel?> get() = _mainUiModel.asLiveData()

    fun onCreate() {
        TODO("Not yet implemented")
    }
}

data class MainUiModel(
    val openScreen: ScreenType
)

enum class ScreenType {
    SplashScreen, ListScreen, Detail, Screen, SettingScreen
}
