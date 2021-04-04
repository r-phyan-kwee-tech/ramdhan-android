package com.marmutech.ramdantimetable.ramadantimetable.di

import com.marmutech.ramdantimetable.ramadantimetable.ui.detail.DetailActivity
import com.marmutech.ramdantimetable.ramadantimetable.ui.schedule.ScheduleListActivity
import com.marmutech.ramdantimetable.ramadantimetable.ui.setting.SettingActivity
import com.marmutech.ramdantimetable.ramadantimetable.ui.splash.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {
    @ContributesAndroidInjector
    internal abstract fun provideListActivity():ScheduleListActivity

    @ContributesAndroidInjector
    internal abstract fun provideDetailActivity():DetailActivity

    @ContributesAndroidInjector
    internal abstract fun provideSettingActivity():SettingActivity

    @ContributesAndroidInjector
    internal abstract fun provideSplashActivity():SplashActivity
}