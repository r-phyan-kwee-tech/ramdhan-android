package com.marmutech.ramdantimetable.ramadantimetable.di

import android.app.Application
import com.marmutech.ramdantimetable.ramadantimetable.RamdanTimtableApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
        modules = [
            AndroidSupportInjectionModule::class,
            AppModule::class,
            ViewModelModule::class,
            ActivityModule::class,
            FragmentModule::class
        ]
)
interface AppComponent : AndroidInjector<RamdanTimtableApp> {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): AppComponent
    }

    override fun inject(ramdanTimtableApp: RamdanTimtableApp)
}
