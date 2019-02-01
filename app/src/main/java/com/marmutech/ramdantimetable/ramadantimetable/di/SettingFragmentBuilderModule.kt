package com.marmutech.ramdantimetable.ramadantimetable.di

import com.marmutech.ramdantimetable.ramadantimetable.ui.setting.CreditFragment
import com.marmutech.ramdantimetable.ramadantimetable.ui.setting.SettingFragment
import com.marmutech.ramdantimetable.ramadantimetable.ui.splash.CountryStateSelectionFragment
import com.marmutech.ramdantimetable.ramadantimetable.ui.splash.FontSelectionFragment
import com.marmutech.ramdantimetable.ramadantimetable.ui.splash.LicenseFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class SettingFragmentBuilderModule {
    @ContributesAndroidInjector
    abstract fun contributeSettingFragment(): SettingFragment

    @ContributesAndroidInjector
    abstract fun contributeCountryStateSelectionFragment(): CountryStateSelectionFragment

    @ContributesAndroidInjector
    abstract fun contributeFontSelectionFragment(): FontSelectionFragment

    @ContributesAndroidInjector
    abstract fun contributeCreditFragment(): CreditFragment

    @ContributesAndroidInjector
    abstract fun contributeLicenseFragment(): LicenseFragment
}