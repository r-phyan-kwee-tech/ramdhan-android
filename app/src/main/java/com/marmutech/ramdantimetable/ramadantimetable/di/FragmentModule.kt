package com.marmutech.ramdantimetable.ramadantimetable.di

import com.marmutech.ramdantimetable.ramadantimetable.ui.detail.duapager.DuaInfoFragment
import com.marmutech.ramdantimetable.ramadantimetable.ui.schedule.InfoBottomSheetFragment
import com.marmutech.ramdantimetable.ramadantimetable.ui.schedule.ScheduleListActivityFragment
import com.marmutech.ramdantimetable.ramadantimetable.ui.setting.CreditFragment
import com.marmutech.ramdantimetable.ramadantimetable.ui.setting.SettingFragment
import com.marmutech.ramdantimetable.ramadantimetable.ui.splash.CountryStateSelectionFragment
import com.marmutech.ramdantimetable.ramadantimetable.ui.splash.FontSelectionFragment
import com.marmutech.ramdantimetable.ramadantimetable.ui.splash.LandingFragment
import com.marmutech.ramdantimetable.ramadantimetable.ui.splash.LicenseFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    internal abstract fun provideScheduleFragment(): ScheduleListActivityFragment

    @ContributesAndroidInjector
    internal abstract fun provideDuaInfoFragment():DuaInfoFragment

    @ContributesAndroidInjector
    internal abstract fun provideSettingFragment():SettingFragment

    @ContributesAndroidInjector
    internal abstract fun provideInfoBottomSheetFragment():InfoBottomSheetFragment

    @ContributesAndroidInjector
    internal abstract fun provideCreditFragment():CreditFragment

    @ContributesAndroidInjector
    internal abstract fun provideLandingFragment():LandingFragment

    @ContributesAndroidInjector
    internal abstract fun provideLicenseFragment():LicenseFragment

    @ContributesAndroidInjector
    internal abstract fun provideFontSelectionFragment():FontSelectionFragment

    @ContributesAndroidInjector
    internal abstract fun provideCountrySelectionFragment():CountryStateSelectionFragment
}