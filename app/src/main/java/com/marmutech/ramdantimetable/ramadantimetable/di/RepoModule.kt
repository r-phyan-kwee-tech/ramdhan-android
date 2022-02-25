package com.marmutech.ramdantimetable.ramadantimetable.di

import com.marmutech.ramdantimetable.ramadantimetable.api.ApiService
import com.marmutech.ramdantimetable.ramadantimetable.db.CountryDao
import com.marmutech.ramdantimetable.ramadantimetable.db.StateDao
import com.marmutech.ramdantimetable.ramadantimetable.db.TimeTableDao
import com.marmutech.ramdantimetable.ramadantimetable.repository.TimeTableRepo
import com.marmutech.ramdantimetable.ramadantimetable.repository.TimeTableRepositoryImpl
import com.marmutech.ramdantimetable.ramadantimetable.repository.UserSettingRepository
import com.marmutech.ramdantimetable.ramadantimetable.repository.UserSettingRepositoryImpl
import com.marmutech.ramdantimetable.ramadantimetable.util.UserPrefUtil
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepoModule {

    @Singleton
    @Provides
    fun provideUserSettingRepo(userPrefUtil: UserPrefUtil): UserSettingRepository =
        UserSettingRepositoryImpl(
            userPrefUtil
        )

    @Singleton
    @Provides
    fun provideCountryRepo(
        countryDao: CountryDao,
        apiService: ApiService,
        userSettingRepository: UserSettingRepository,
        stateDao: StateDao,
        timeTableDao: TimeTableDao
    ): TimeTableRepo = TimeTableRepositoryImpl(
        countryDao,
        apiService,
        userSettingRepository,
        stateDao,
        timeTableDao
    )
}
