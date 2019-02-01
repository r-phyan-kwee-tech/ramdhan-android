package com.marmutech.ramdantimetable.ramadantimetable.ui.schedule

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.marmutech.ramdantimetable.ramadantimetable.R
import com.marmutech.ramdantimetable.ramadantimetable.di.Injectable
import com.marmutech.ramdantimetable.ramadantimetable.ui.setting.SettingActivity
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_info_bottom_sheet.*

class InfoBottomSheetFragment : BottomSheetDialogFragment(), Injectable, View.OnClickListener {

    companion object {
        val TAG: String = "tag_info_bottom_sheet"
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_info_bottom_sheet, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        ll_choose_location.setOnClickListener(this)
        ll_font_support.setOnClickListener(this)
        ll_credits.setOnClickListener(this)
        ll_open_source_libraries.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ll_choose_location -> showInfoFragments(SettingActivity.FLAG_CHOOSE_LOCATION)
            R.id.ll_font_support -> showInfoFragments(SettingActivity.FLAG_FONT_SUPPORT)
            R.id.ll_credits -> showInfoFragments(SettingActivity.FLAG_CREDITS)
            R.id.ll_open_source_libraries -> showInfoFragments(SettingActivity.FLAG_OPEN_SOURCE)
        }
    }

    private fun showInfoFragments(targetFlags: String) {
        (activity as ScheduleListActivity).lunchInfoActivity(targetFlags)
    }
}