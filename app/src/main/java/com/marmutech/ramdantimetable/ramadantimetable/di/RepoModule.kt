package com.marmutech.ramdantimetable.ramadantimetable.di

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
    fun provideUserSettingRepo(userPrefUtil: UserPrefUtil): UserSettingRepository = UserSettingRepositoryImpl(
        userPrefUtil
    )
}
