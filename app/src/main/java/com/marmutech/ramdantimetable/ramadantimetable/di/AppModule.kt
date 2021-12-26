package com.marmutech.ramdantimetable.ramadantimetable.di

import android.app.Application
import androidx.room.Room
import com.marmutech.ramdantimetable.ramadantimetable.api.ApiService
import com.marmutech.ramdantimetable.ramadantimetable.db.CountryDao
import com.marmutech.ramdantimetable.ramadantimetable.db.RamdanDb
import com.marmutech.ramdantimetable.ramadantimetable.db.StateDao
import com.marmutech.ramdantimetable.ramadantimetable.db.TimeTableDao
import com.marmutech.ramdantimetable.ramadantimetable.util.CommonUtil
import com.marmutech.ramdantimetable.ramadantimetable.util.UserPrefUtil
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideApiService(loggingInterceptor: HttpLoggingInterceptor): ApiService {
        val client = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()
        return Retrofit.Builder()
            .client(client)
            .baseUrl("https://ramdan-api-mm.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return logging
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

    @Singleton
    @Provides
    fun provideCommonUtil(app: Application): CommonUtil {
        return CommonUtil(app)
    }

    @Provides
    @MainCoroutineDispatcher
    fun provideMainDispatcher(): CoroutineDispatcher = Dispatchers.Main

    @Provides
    @IOCoroutineDispatcher
    fun provideIODispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @DefaultCoroutineDispatcher
    fun provideDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default
}

@Qualifier
annotation class IOCoroutineDispatcher

@Qualifier
annotation class MainCoroutineDispatcher

@Qualifier
annotation class DefaultCoroutineDispatcher
