package com.marmutech.ramdantimetable.ramadantimetable.ui.schedule

import android.databinding.DataBindingUtil
import android.support.v7.util.DiffUtil
import android.support.v7.util.DiffUtil.calculateDiff
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.marmutech.ramdantimetable.ramadantimetable.R
import com.marmutech.ramdantimetable.ramadantimetable.databinding.RowScheduleListBinding
import com.marmutech.ramdantimetable.ramadantimetable.model.TimeTableDay

class ScheduleAdapter(var clickCallBack: ScheduleClickCallBack) : RecyclerView.Adapter<ScheduleViewHolder>() {

    internal var mScheduleList: List<out TimeTableDay>? = listOf()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder {
        var binding: RowScheduleListBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.row_schedule_list, parent, false)
        binding.callback = clickCallBack
        return ScheduleViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return if (mScheduleList == null) 0 else mScheduleList?.size as Int
    }

    override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {
        holder.mBinding?.day = mScheduleList?.get(position)
        holder.mBinding?.executePendingBindings()
    }

    fun setScheduleList(scheduleList: List<TimeTableDay>?) {
        if (mScheduleList == null) {
            mScheduleList = scheduleList
            notifyItemRangeInserted(0, scheduleList?.size as Int)
        } else {
            var result: DiffUtil.DiffResult = calculateDiff(object : DiffUtil.Callback() {
                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    var mLocalScheduleList = mScheduleList
                    return mLocalScheduleList?.get(oldItemPosition)?.id == scheduleList?.get(newItemPosition)?.id
                }

                override fun getOldListSize(): Int {
                    var mLocalScheduleList = mScheduleList
                    return if (mLocalScheduleList != null) mLocalScheduleList.size else 0
                }

                override fun getNewListSize(): Int {
                    return scheduleList?.size as Int
                }

                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    var mLocalScheduleList = mScheduleList

                    var oldObj: TimeTableDay = mLocalScheduleList?.get(oldItemPosition) as TimeTableDay

                    var newObj: TimeTableDay = scheduleList?.get(newItemPosition) as TimeTableDay

                    return oldObj.countryId == newObj.countryId
                            && oldObj.day == newObj.day


                }
            })
        }
    }


}