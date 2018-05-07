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
import com.marmutech.ramdantimetable.ramadantimetable.util.UserPrefUtil
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class AppModule {
    @Singleton
    @Provides
    fun provideCountryService(): CountryService {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder().addInterceptor(logging).build()
        return Retrofit.Builder()
                .client(client)
                .baseUrl("https://ramdan-api.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .build()
                .create(CountryService::class.java)
    }

    @Singleton
    @Provides
    fun provideStateService(): StateService {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder().addInterceptor(logging).build()
        return Retrofit.Builder()
                .client(client)
                .baseUrl("https://ramdan-api.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .build()
                .create(StateService::class.java)
    }

    @Singleton
    @Provides
    fun provideTimetableDayService(): TimeTableDayServie {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder().addInterceptor(logging).build()
        return Retrofit.Builder()
                .client(client)
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

    @Singleton
    @Provides
    fun provideSharePreference(app: Application): UserPrefUtil {
        return UserPrefUtil(app)
    }

}