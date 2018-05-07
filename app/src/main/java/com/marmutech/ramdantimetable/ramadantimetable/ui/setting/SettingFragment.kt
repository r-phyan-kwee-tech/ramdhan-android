package com.marmutech.ramdantimetable.ramadantimetable.ui.setting

import android.arch.lifecycle.ViewModelProvider
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.marmutech.ramdantimetable.ramadantimetable.di.Injectable
import com.marmutech.ramdantimetable.ramadantimetable.util.UserPrefUtil
import javax.inject.Inject

class SettingFragment : Fragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var userPrefUtil: UserPrefUtil

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return super.onCreateView(inflater, container, savedInstanceState)
    }
}