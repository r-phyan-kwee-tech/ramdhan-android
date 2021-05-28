package com.marmutech.ramdantimetable.ramadantimetable.util

import android.app.Application
import android.content.SharedPreferences
import android.preference.PreferenceManager
import javax.inject.Inject

class UserPrefUtil @Inject constructor(app: Application) {

    private val PREF_STATE_ID = "pref_state_id"
    private val PREF_STATE_NAME = "pref_state_name"
    private val PREF_LOCATION_ID = "pref_location_id"
    private val PREF_LOCATION_NAME = "pref_location_name"
    private val PREF_FONT = "pref_font"
    private val SPLASH_FINISH = "pref_splash"

    private val mSharedPreference: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(
        app.applicationContext
    )
//    private lateinit var mApplication: Application

//    companion object {
//        private lateinit var userPrefUtil: UserPrefUtil
//
//        fun getInstance(app: Application): UserPrefUtil {
//            if (userPrefUtil == null) {
//                userPrefUtil = UserPrefUtil(app)
//            }
//            return userPrefUtil
//        }
//
//    }

//    init {
//        mApplication = app
//        mSharedPreference = PreferenceManager.getDefaultSharedPreferences(mApplication)
//    }


    fun setSplashFinished(isFinished: Boolean) {
        mSharedPreference.edit().putBoolean(SPLASH_FINISH, isFinished).apply()
    }

    fun isSplashFinished(): Boolean {
        return mSharedPreference.getBoolean(SPLASH_FINISH, false)
    }

    fun saveStateId(idState: String) {
        mSharedPreference.edit().putString(PREF_STATE_ID, idState).apply()
    }

    fun saveStateName(nameState: String) {
        mSharedPreference.edit().putString(PREF_STATE_NAME, nameState).apply()
    }

    fun saveLocationId(idLocation: String) {
        mSharedPreference.edit().putString(PREF_LOCATION_ID, idLocation).apply()
    }

    fun saveLoactionName(nameLocation: String) {
        mSharedPreference.edit().putString(PREF_LOCATION_NAME, nameLocation).apply()
    }

    fun getStateId(): String {
        return mSharedPreference.getString(PREF_STATE_ID, "")
    }

    fun getStateName(): String {
        return mSharedPreference.getString(PREF_STATE_NAME, "")
    }

    fun getLocationId(): String? {
        return mSharedPreference.getString(PREF_LOCATION_ID, null)
    }

    fun getLocationName(): String {
        return mSharedPreference.getString(PREF_LOCATION_NAME, "")
    }

    /***
     * default will return  zawgyi if you want to enable unicode then setFontTrue
     */
    fun setFont(isUnicode: Boolean) {
        mSharedPreference.edit().putBoolean(PREF_FONT, isUnicode).apply()
    }

    /***
     * default will return  zawgyi if you want to enable unicode then setFontTrue
     */
    fun getFont(): Boolean {
        return mSharedPreference.getBoolean(PREF_FONT, false)
    }
}
