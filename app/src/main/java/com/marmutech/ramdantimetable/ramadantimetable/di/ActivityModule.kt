package com.marmutech.ramdantimetable.ramadantimetable.di

import com.marmutech.ramdantimetable.ramadantimetable.ui.detail.DetailActivity
import com.marmutech.ramdantimetable.ramadantimetable.ui.schedule.ScheduleListActivity
import com.marmutech.ramdantimetable.ramadantimetable.ui.setting.SettingActivity
import com.marmutech.ramdantimetable.ramadantimetable.ui.splash.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {
    @ActivityScope
    @ContributesAndroidInjector
    internal abstract fun provideListActivity():ScheduleListActivity

    @ActivityScope
    @ContributesAndroidInjector
    internal abstract fun provideDetailActivity():DetailActivity

    @ActivityScope
    @ContributesAndroidInjector
    internal abstract fun provideSettingActivity():SettingActivity

    @ActivityScope
    @ContributesAndroidInjector
    internal abstract fun provideSplashActivity():SplashActivity
}
