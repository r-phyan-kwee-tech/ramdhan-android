package com.marmutech.ramdantimetable.ramadantimetable.ui.setting

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.marmutech.ramdantimetable.ramadantimetable.R
import com.marmutech.ramdantimetable.ramadantimetable.di.Injectable
import com.marmutech.ramdantimetable.ramadantimetable.util.UserPrefUtil
import kotlinx.android.synthetic.main.fragment_setting.*
import javax.inject.Inject

class SettingFragment : Fragment(), Injectable, View.OnClickListener {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var userPrefUtil: UserPrefUtil

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        ll_location_layout.setOnClickListener(this)
        ll_state_layout.setOnClickListener(this)

        val viewModel: SettingViewModel = ViewModelProviders.of(this, viewModelFactory).get(SettingViewModel::class.java)
        subscribeUi(viewModel)
    }

    private fun subscribeUi(viewModel: SettingViewModel) {

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ll_location_layout -> (activity as SettingActivity).showLocationSelectBottomSheet()
            R.id.ll_state_layout -> (activity as SettingActivity).showStateSelectBottomSheet()
        }
    }
}