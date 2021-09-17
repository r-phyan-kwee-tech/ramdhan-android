package com.marmutech.ramdantimetable.ramadantimetable.ui.schedule

import androidx.recyclerview.widget.RecyclerView
import com.marmutech.ramdantimetable.ramadantimetable.databinding.RowScheduleListBinding
import com.marmutech.ramdantimetable.ramadantimetable.model.TimeTableDay

class ScheduleViewHolder(
    private val binding: RowScheduleListBinding,
    clickCallBack: ((TimeTableDay) -> Unit)
) :
    RecyclerView.ViewHolder(binding.root) {

    private lateinit var timeTableDay: TimeTableDay

    init {
        binding.rowCardView.setOnClickListener {
            clickCallBack.invoke(timeTableDay)
        }
    }

    fun bind(timeTableDay: TimeTableDay) {
        this.timeTableDay = timeTableDay
        with(binding) {
            tvDayScheduleRow.text =
                root.context.getString(R.string.str_day, timeTableDay.day.toString())
            tvStartTimeScheduleRow.text = timeTableDay.sehriTime
            tvEndTimeScheduleRow.text = timeTableDay.iftariTime
        }
    }
}
