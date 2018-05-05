package com.marmutech.ramdantimetable.ramadantimetable.di

import com.marmutech.ramdantimetable.ramadantimetable.ui.splash.CompleteFragment
import com.marmutech.ramdantimetable.ramadantimetable.ui.splash.CountryStateSelectionFragment
import com.marmutech.ramdantimetable.ramadantimetable.ui.splash.FontSelectionFragment
import com.marmutech.ramdantimetable.ramadantimetable.ui.splash.LandingFragment
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

    @ContributesAndroidInjector
    abstract fun contributeCompleteFragment(): CompleteFragment
}