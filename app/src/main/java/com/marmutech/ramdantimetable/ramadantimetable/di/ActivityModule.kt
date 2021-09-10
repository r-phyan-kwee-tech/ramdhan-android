package com.marmutech.ramdantimetable.ramadantimetable.di

import com.marmutech.ramdantimetable.ramadantimetable.ui.MainActivity
import com.marmutech.ramdantimetable.ramadantimetable.ui.setting.SettingActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ActivityScope
    @ContributesAndroidInjector
    internal abstract fun provideSettingActivity(): SettingActivity

    @ActivityScope
    @ContributesAndroidInjector
    internal abstract fun provideMainActivity(): MainActivity
}
