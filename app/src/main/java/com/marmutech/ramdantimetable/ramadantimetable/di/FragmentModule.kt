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
import com.marmutech.ramdantimetable.ramadantimetable.ui.splash.OnBoardingFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

    @FragmentScope
    @ContributesAndroidInjector
    internal abstract fun provideScheduleFragment(): ScheduleListActivityFragment

    @FragmentScope
    @ContributesAndroidInjector
    internal abstract fun provideDuaInfoFragment(): DuaInfoFragment

    @FragmentScope
    @ContributesAndroidInjector
    internal abstract fun provideSettingFragment(): SettingFragment

    @FragmentScope
    @ContributesAndroidInjector
    internal abstract fun provideInfoBottomSheetFragment(): InfoBottomSheetFragment

    @FragmentScope
    @ContributesAndroidInjector
    internal abstract fun provideCreditFragment(): CreditFragment

    @FragmentScope
    @ContributesAndroidInjector
    internal abstract fun provideLandingFragment(): LandingFragment

    @FragmentScope
    @ContributesAndroidInjector
    internal abstract fun provideLicenseFragment(): LicenseFragment

    @FragmentScope
    @ContributesAndroidInjector
    internal abstract fun provideFontSelectionFragment(): FontSelectionFragment

    @FragmentScope
    @ContributesAndroidInjector
    internal abstract fun provideCountrySelectionFragment(): CountryStateSelectionFragment

    @FragmentScope
    @ContributesAndroidInjector
    internal abstract fun provideOnBoardingFragment(): OnBoardingFragment
}
