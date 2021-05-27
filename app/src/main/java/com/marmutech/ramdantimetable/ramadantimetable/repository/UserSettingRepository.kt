package com.marmutech.ramdantimetable.ramadantimetable.repository

import com.marmutech.ramdantimetable.ramadantimetable.util.UserPrefUtil
import javax.inject.Inject

class UserSettingRepositoryImpl @Inject constructor(private val userPrefUtil: UserPrefUtil) :
    UserSettingRepository {

    override suspend fun setIsEnableUnicode(isEnable: Boolean) = userPrefUtil.setFont(isEnable)

    override suspend fun getIsEnableUnicode(): Boolean = userPrefUtil.getFont()

    override suspend fun getSelectedCountryId(): String? = userPrefUtil.getLocationId()

    override suspend fun saveSelectedCountryId(id: String) = userPrefUtil.saveLocationId(id)

}

interface UserSettingRepository {
    suspend fun setIsEnableUnicode(isEnable: Boolean)
    suspend fun getIsEnableUnicode(): Boolean

    suspend fun getSelectedCountryId(): String?
    suspend fun saveSelectedCountryId(id: String)
}
