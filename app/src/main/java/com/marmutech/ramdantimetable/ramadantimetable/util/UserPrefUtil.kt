package com.marmutech.ramdantimetable.ramadantimetable.util

import android.app.Application
import android.content.SharedPreferences
import android.preference.PreferenceManager

class UserPrefUtil(app: Application) {

    private val PREF_STATE_ID = "pref_state_id"
    private val PREF_STATE_NAME = "pref_state_name"
    private val PREF_FONT = "pref_font"

    private lateinit var mSharedPreference: SharedPreferences
    private lateinit var mApplication: Application

    companion object {
        private lateinit var userPrefUtil: UserPrefUtil

        fun getInstance(app: Application): UserPrefUtil {
            if (userPrefUtil == null) {
                userPrefUtil = UserPrefUtil(app)
            }
            return userPrefUtil
        }

    }

    init {
        mApplication = app
        mSharedPreference = PreferenceManager.getDefaultSharedPreferences(mApplication)
    }

    fun saveStateId(idState: String) {
        mSharedPreference.edit().putString(PREF_STATE_ID, idState).apply()
    }

    fun saveStateName(nameState: String) {
        mSharedPreference.edit().putString(PREF_STATE_NAME, nameState).apply()
    }

    fun getStateId(): String {
        return mSharedPreference.getString(PREF_STATE_ID, "")
    }

    fun getStateName(): String {
        return mSharedPreference.getString(PREF_STATE_NAME, "")
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