package com.marmutech.ramdantimetable.ramadantimetable.ui.schedule

import com.marmutech.ramdantimetable.ramadantimetable.databinding.RowScheduleListBinding

class ScheduleViewHolder(binding: RowScheduleListBinding) : androidx.recyclerview.widget.RecyclerView.ViewHolder(binding.root) {
    var mBinding: RowScheduleListBinding? = null

    init {
        this.mBinding = binding
    }
}