package com.marmutech.ramdantimetable.ramadantimetable.di

import android.app.Application
import android.arch.persistence.room.Room
import com.marmutech.ramdantimetable.ramadantimetable.api.CountryService
import com.marmutech.ramdantimetable.ramadantimetable.api.StateService
import com.marmutech.ramdantimetable.ramadantimetable.api.TimeTableDayServie
import com.marmutech.ramdantimetable.ramadantimetable.db.CountryDao
import com.marmutech.ramdantimetable.ramadantimetable.db.RamdanDb
import com.marmutech.ramdantimetable.ramadantimetable.db.StateDao
import com.marmutech.ramdantimetable.ramadantimetable.db.TimeTableDao
import com.marmutech.ramdantimetable.ramadantimetable.util.LiveDataCallAdapterFactory
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class AppModule {
    @Singleton
    @Provides
    fun provideCountryService(): CountryService {
        return Retrofit.Builder()
                .baseUrl("https://ramdan-api.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .build()
                .create(CountryService::class.java)
    }

    @Singleton
    @Provides
    fun provideStateService(): StateService {
        return Retrofit.Builder()
                .baseUrl("https://ramdan-api.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .build()
                .create(StateService::class.java)
    }

    @Singleton
    @Provides
    fun provideTimetableDayService(): TimeTableDayServie {
        return Retrofit.Builder()
                .baseUrl("https://ramdan-api.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .build()
                .create(TimeTableDayServie::class.java)
    }


    @Singleton
    @Provides
    fun provideDb(app: Application): RamdanDb {
        return Room
                .databaseBuilder(app, RamdanDb::class.java, "ramdan_timetable.db")
                .fallbackToDestructiveMigration()
                .build()
    }

    @Singleton
    @Provides
    fun provideCountryDao(db: RamdanDb): CountryDao {
        return db.countryDao()
    }

    @Singleton
    @Provides
    fun provideStateDao(db: RamdanDb): StateDao {
        return db.stateDao()
    }

    @Singleton
    @Provides
    fun provideTimeTableDayDao(db: RamdanDb): TimeTableDao {
        return db.timetableDao()
    }
}