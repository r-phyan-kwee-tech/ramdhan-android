package com.marmutech.ramdantimetable.ramadantimetable.di

import com.marmutech.ramdantimetable.ramadantimetable.ui.setting.SettingFragment
import com.marmutech.ramdantimetable.ramadantimetable.ui.splash.CountryStateSelectionFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class SettingFragmentBuilderModule {
    @ContributesAndroidInjector
    abstract fun contributeSettingFragment(): SettingFragment

    @ContributesAndroidInjector
    abstract fun contributeCountryStateSelectionFragment(): CountryStateSelectionFragment
}