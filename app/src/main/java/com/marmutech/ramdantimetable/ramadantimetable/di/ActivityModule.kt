package com.marmutech.ramdantimetable.ramadantimetable.di

import com.marmutech.ramdantimetable.ramadantimetable.ui.MainActivity
import com.marmutech.ramdantimetable.ramadantimetable.ui.detail.DetailActivity
import com.marmutech.ramdantimetable.ramadantimetable.ui.schedule.LegacyScheduleListActivity
import com.marmutech.ramdantimetable.ramadantimetable.ui.setting.SettingActivity
import com.marmutech.ramdantimetable.ramadantimetable.ui.splash.LegacySplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {
    @ActivityScope
    @ContributesAndroidInjector
    internal abstract fun provideListActivity(): LegacyScheduleListActivity

    @ActivityScope
    @ContributesAndroidInjector
    internal abstract fun provideDetailActivity(): DetailActivity

    @ActivityScope
    @ContributesAndroidInjector
    internal abstract fun provideSettingActivity(): SettingActivity

    @ActivityScope
    @ContributesAndroidInjector
    internal abstract fun provideSplashActivity(): LegacySplashActivity

    @ActivityScope
    @ContributesAndroidInjector
    internal abstract fun provideMainActivity(): MainActivity
}
