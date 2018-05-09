package com.marmutech.ramdantimetable.ramadantimetable.di

import com.marmutech.ramdantimetable.ramadantimetable.ui.splash.*
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class SplashFragmentBuilderModule {
    @ContributesAndroidInjector
    abstract fun contributeLandingFragment(): LandingFragment

    @ContributesAndroidInjector
    abstract fun contributeFontSelectionFragment(): FontSelectionFragment

    @ContributesAndroidInjector
    abstract fun contributeCountryStateSelectionFragment(): CountryStateSelectionFragment


}