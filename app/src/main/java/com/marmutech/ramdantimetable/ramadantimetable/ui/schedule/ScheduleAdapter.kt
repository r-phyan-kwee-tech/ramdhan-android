package com.marmutech.ramdantimetable.ramadantimetable.ui.schedule

import android.support.v7.util.DiffUtil
import android.support.v7.util.DiffUtil.calculateDiff
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.marmutech.ramdantimetable.ramadantimetable.model.TimeTableDay

class ScheduleAdapter(var clickCallBack: ScheduleClickCallBack) : RecyclerView.Adapter<ScheduleViewHolder>() {

    internal var mScheduleList: List<out TimeTableDay>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun setScheduleList(scheduleList: List<TimeTableDay>?) {
        if (mScheduleList == null) {
            mScheduleList = scheduleList
            notifyItemRangeInserted(0, scheduleList?.size as Int)
        } else {
            var result: DiffUtil.DiffResult = calculateDiff(object : DiffUtil.Callback() {
                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun getOldListSize(): Int {
                    var mLocalScheduleList = mScheduleList
                    return if (mLocalScheduleList != null) mLocalScheduleList.size else 0
                }

                override fun getNewListSize(): Int {
                    return scheduleList?.size as Int
                }

                override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
                    return super.getChangePayload(oldItemPosition, newItemPosition)
                }

                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }
            })
        }
    }


}