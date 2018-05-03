package com.marmutech.ramdantimetable.ramadantimetable.ui.schedule

import android.support.v7.widget.RecyclerView
import com.marmutech.ramdantimetable.ramadantimetable.databinding.RowScheduleListBinding
import com.marmutech.ramdantimetable.ramadantimetable.di.AppInjector.init

class ScheduleViewHolder(private var binding: RowScheduleListBinding) : RecyclerView.ViewHolder(binding.root) {
    var mBinding: RowScheduleListBinding? = null

    init {
        mBinding=binding
    }
}