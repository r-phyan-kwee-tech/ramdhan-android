package com.marmutech.ramdantimetable.ramadantimetable.repository

import com.marmutech.ramdantimetable.ramadantimetable.util.UserPrefUtil
import javax.inject.Inject

class UserSettingRepositoryImpl @Inject constructor(private val userPrefUtil: UserPrefUtil) :
    UserSettingRepository {

    override suspend fun setIsEnableUnicode(isEnable: Boolean) = userPrefUtil.setFont(isEnable)

    override suspend fun getIsEnableUnicode(): Boolean = userPrefUtil.getFont()

    override suspend fun getSelectedCountryName(): String? = userPrefUtil.getLocationName()

    override suspend fun saveSelectedCountryName(name: String) = userPrefUtil.saveLoactionName(name)

    override suspend fun getSelectedCountryId(): String? = userPrefUtil.getLocationId()

    override suspend fun saveSelectedCountryId(id: String) = userPrefUtil.saveLocationId(id)

    override suspend fun getSelectedStateName(): String? = userPrefUtil.getStateName()

    override suspend fun saveSelectedStateName(name: String) = userPrefUtil.saveStateName(name)

    override suspend fun getSelectedStateId(): String? = userPrefUtil.getStateId()

    override suspend fun saveSelectedStateId(id: String) = userPrefUtil.saveStateId(id)
    override suspend fun getIsOnBoardingFinish(): Boolean {
        return userPrefUtil.isSplashFinished()
    }

    override suspend fun saveIsOnBoardingFinish(value: Boolean) {
        return userPrefUtil.setSplashFinished(value)
    }

}

interface UserSettingRepository {
    suspend fun setIsEnableUnicode(isEnable: Boolean)
    suspend fun getIsEnableUnicode(): Boolean

    suspend fun getSelectedCountryName(): String?
    suspend fun saveSelectedCountryName(name: String)

    suspend fun getSelectedCountryId(): String?
    suspend fun saveSelectedCountryId(id: String)

    suspend fun getSelectedStateName(): String?
    suspend fun saveSelectedStateName(name: String)

    suspend fun getSelectedStateId(): String?
    suspend fun saveSelectedStateId(id: String)

    suspend fun getIsOnBoardingFinish(): Boolean
    suspend fun saveIsOnBoardingFinish(value: Boolean)
}
