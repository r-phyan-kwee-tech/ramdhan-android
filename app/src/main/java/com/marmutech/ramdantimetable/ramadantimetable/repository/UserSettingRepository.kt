package com.marmutech.ramdantimetable.ramadantimetable.repository

import com.marmutech.ramdantimetable.ramadantimetable.util.UserPrefUtil
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserSettingRepositoryImpl @Inject constructor(private val userPrefUtil: UserPrefUtil) :
    UserSettingRepository {
    override suspend fun setIsEnableUnicode(isEnable: Boolean) = flow {
        userPrefUtil.setFont(isEnable)
        emit(isEnable)
    }

    override suspend fun getIsEnableUnicode(): Flow<Boolean> = flow { emit(userPrefUtil.getFont()) }

}

interface UserSettingRepository {
    suspend fun setIsEnableUnicode(isEnable: Boolean): Flow<Boolean>
    suspend fun getIsEnableUnicode(): Flow<Boolean>
}
