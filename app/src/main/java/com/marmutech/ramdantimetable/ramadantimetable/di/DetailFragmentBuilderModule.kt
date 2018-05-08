package com.marmutech.ramdantimetable.ramadantimetable.di

import com.marmutech.ramdantimetable.ramadantimetable.ui.detail.duapager.DuaInfoFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class DetailFragmentBuilderModule {
    @ContributesAndroidInjector
    abstract fun contrubuteDuaInforagment(): DuaInfoFragment
}