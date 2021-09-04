package com.marmutech.ramdantimetable.ramadantimetable.ui.schedule

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DiffUtil.calculateDiff
import com.marmutech.ramdantimetable.ramadantimetable.R
import com.marmutech.ramdantimetable.ramadantimetable.databinding.RowScheduleListBinding
import com.marmutech.ramdantimetable.ramadantimetable.model.TimeTableDay
import timber.log.Timber

class ScheduleAdapter(var clickCallBack: ScheduleClickCallBack) :
    androidx.recyclerview.widget.RecyclerView.Adapter<ScheduleViewHolder>() {

    private var mScheduleList: List<out TimeTableDay>? = listOf()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder {
        var binding: RowScheduleListBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.row_schedule_list,
            parent,
            false
        )
        binding.callback = clickCallBack
        return ScheduleViewHolder(binding)
    }

    override fun getItemCount() = if (mScheduleList != null) mScheduleList?.size as Int else 0


    override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {
        Timber.d("schedule item " + mScheduleList?.get(position))
        holder.mBinding?.dayObj = mScheduleList?.get(position)
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
                    return mLocalScheduleList?.get(oldItemPosition)?.id == scheduleList?.get(
                        newItemPosition
                    )?.id
                }

                override fun getOldListSize(): Int {
                    var mLocalScheduleList = mScheduleList
                    return if (mLocalScheduleList != null) mLocalScheduleList.size else 0
                }

                override fun getNewListSize() = scheduleList?.size ?: 0

                override fun areContentsTheSame(
                    oldItemPosition: Int,
                    newItemPosition: Int
                ): Boolean {
                    var mLocalScheduleList = mScheduleList

                    var oldObj: TimeTableDay =
                        mLocalScheduleList?.get(oldItemPosition) as TimeTableDay

                    var newObj: TimeTableDay = scheduleList?.get(newItemPosition) as TimeTableDay

                    return oldObj.countryId == newObj.countryId
                            && oldObj.day == newObj.day


                }
            })
            mScheduleList = scheduleList
            result.dispatchUpdatesTo(this)
        }
    }


}
