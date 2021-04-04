package com.marmutech.ramdantimetable.ramadantimetable.di

import android.app.Application
import com.marmutech.ramdantimetable.ramadantimetable.RamdanTimtableApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
        modules = [
            AndroidSupportInjectionModule::class,
            AndroidInjectionModule::class,
            AppModule::class,
            DetailActivityModule::class,
            ScheduleListActivityModule::class,
            SplashActivityModule::class,
            SettingActivityBuilderModule::class
        ]
)
interface AppComponent : AndroidInjector<RamdanTimtableApp> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    override fun inject(ramdanTimtableApp: RamdanTimtableApp)
}