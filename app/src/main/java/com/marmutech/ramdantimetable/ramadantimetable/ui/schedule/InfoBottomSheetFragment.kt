package com.marmutech.ramdantimetable.ramadantimetable.ui.schedule

import android.os.Bundle
import android.support.design.widget.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.marmutech.ramdantimetable.ramadantimetable.R
import com.marmutech.ramdantimetable.ramadantimetable.di.Injectable

class InfoBottomSheetFragment : BottomSheetDialogFragment(), Injectable {

    companion object {
        val TAG:String="tag_info_bottom_sheet"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_info_bottom_sheet, container, false)
    }
}