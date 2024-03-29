package com.marmutech.ramdantimetable.ramadantimetable.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.marmutech.ramdantimetable.ramadantimetable.ui.MainViewModel
import com.marmutech.ramdantimetable.ramadantimetable.ui.schedule.ScheduleViewModel
import com.marmutech.ramdantimetable.ramadantimetable.ui.splash.SplashViewModel
import com.marmutech.ramdantimetable.ramadantimetable.viewmodel.RamdanTimeTableViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ScheduleViewModel::class)
    abstract fun bindScheduleListViewModel(scheduleViewModel: ScheduleViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    abstract fun bindSplashViewModel(splashViewModel: SplashViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(mainViewModel: MainViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: RamdanTimeTableViewModelFactory): ViewModelProvider.Factory
}
