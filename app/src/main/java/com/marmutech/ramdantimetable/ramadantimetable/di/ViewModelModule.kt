package com.marmutech.ramdantimetable.ramadantimetable.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.marmutech.ramdantimetable.ramadantimetable.ui.detail.DetailViewModel
import com.marmutech.ramdantimetable.ramadantimetable.ui.schedule.ScheduleViewModel
import com.marmutech.ramdantimetable.ramadantimetable.ui.setting.SettingViewModel
import com.marmutech.ramdantimetable.ramadantimetable.ui.splash.FontSelectionViewModel
import com.marmutech.ramdantimetable.ramadantimetable.ui.splash.SplashViewModel
import com.marmutech.ramdantimetable.ramadantimetable.viewmodel.RamdanTimeTableViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Suppress("unused")
@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    abstract fun bindSplashViewModel(detailViewModel: SplashViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailViewModel::class)
    abstract fun bindDetailViewModel(detailViewModel: DetailViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ScheduleViewModel::class)
    abstract fun bindScheduleListViewModel(scheduleViewModel: ScheduleViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SettingViewModel::class)
    abstract fun bindSettingViewModel(settingViewModel: SettingViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FontSelectionViewModel::class)
    abstract fun bindFontSelectionViewModel(fontSelectionViewModel: FontSelectionViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: RamdanTimeTableViewModelFactory): ViewModelProvider.Factory
}
