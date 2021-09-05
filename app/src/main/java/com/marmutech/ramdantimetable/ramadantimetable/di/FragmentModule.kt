package com.marmutech.ramdantimetable.ramadantimetable.di

import com.marmutech.ramdantimetable.ramadantimetable.ui.detail.DetailViewFragment
import com.marmutech.ramdantimetable.ramadantimetable.ui.detail.duapager.DuaInfoFragment
import com.marmutech.ramdantimetable.ramadantimetable.ui.schedule.InfoBottomSheetFragment
import com.marmutech.ramdantimetable.ramadantimetable.ui.schedule.ScheduleFragment
import com.marmutech.ramdantimetable.ramadantimetable.ui.setting.CreditFragment
import com.marmutech.ramdantimetable.ramadantimetable.ui.setting.SettingFragment
import com.marmutech.ramdantimetable.ramadantimetable.ui.splash.*
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

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

    @FragmentScope
    @ContributesAndroidInjector
    internal abstract fun provideScheduleFragment(): ScheduleFragment

    @FragmentScope
    @ContributesAndroidInjector
    internal abstract fun provideDetailViewFragment(): DetailViewFragment
}
