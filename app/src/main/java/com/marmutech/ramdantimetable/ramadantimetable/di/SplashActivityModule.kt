package com.marmutech.ramdantimetable.ramadantimetable.di

import com.marmutech.ramdantimetable.ramadantimetable.ui.splash.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class SplashActivityModule {
    @ContributesAndroidInjector(modules = [SplashFragmentBuilderModule::class])
    abstract fun contributeScheduleListActivity(): SplashActivity
}