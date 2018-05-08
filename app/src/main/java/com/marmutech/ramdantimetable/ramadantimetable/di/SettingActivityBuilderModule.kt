package com.marmutech.ramdantimetable.ramadantimetable.di

import com.marmutech.ramdantimetable.ramadantimetable.ui.setting.SettingActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class SettingActivityBuilderModule {
    @ContributesAndroidInjector(modules = [SettingFragmentBuilderModule::class])
    abstract fun contributeSettingActivity(): SettingActivity

}