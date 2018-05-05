package com.marmutech.ramdantimetable.ramadantimetable.ui.splash

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.marmutech.ramdantimetable.ramadantimetable.R
import com.marmutech.ramdantimetable.ramadantimetable.util.UserPrefUtil
import javax.inject.Inject

class SplashActivity : AppCompatActivity() {

    @Inject
    lateinit var userPref: UserPrefUtil

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        userPref.getStateId()
        Log.e("PREF", userPref.getStateId())
    }
}
