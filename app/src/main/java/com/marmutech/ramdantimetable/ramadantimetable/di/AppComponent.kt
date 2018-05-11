package com.marmutech.ramdantimetable.ramadantimetable.di

import android.app.Application
import com.marmutech.ramdantimetable.ramadantimetable.RamdanTimtableApp

import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
        modules = [
            AndroidInjectionModule::class,
            AppModule::class,
            DetailActivityModule::class,
            ScheduleListActivityModule::class,
            SplashActivityModule::class,
            SettingActivityBuilderModule::class
        ]
)
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(ramdanTimtableApp: RamdanTimtableApp)
}