package com.marmutech.ramdantimetable.ramadantimetable.di

import com.marmutech.ramdantimetable.ramadantimetable.ui.schedule.InfoBottomSheetFragment
import com.marmutech.ramdantimetable.ramadantimetable.ui.schedule.ScheduleListActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class ScheduleListActivityModule {
    @ContributesAndroidInjector(modules = [ScheduleListFragmentModule::class])
    abstract fun contributeScheduleListActivity(): ScheduleListActivity


    @ContributesAndroidInjector
    abstract fun contributInfoBottomSheetFragment(): InfoBottomSheetFragment
}