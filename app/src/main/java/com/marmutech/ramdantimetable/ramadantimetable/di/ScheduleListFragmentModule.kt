package com.marmutech.ramdantimetable.ramadantimetable.di

import com.marmutech.ramdantimetable.ramadantimetable.ui.schedule.ScheduleListActivityFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class ScheduleListFragmentModule {
    @ContributesAndroidInjector
    abstract fun contributeScheduleListActivityFragment(): ScheduleListActivityFragment
}