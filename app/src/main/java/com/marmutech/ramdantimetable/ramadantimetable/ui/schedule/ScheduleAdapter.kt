package com.marmutech.ramdantimetable.ramadantimetable.ui.schedule

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DiffUtil.calculateDiff
import androidx.recyclerview.widget.RecyclerView
import com.marmutech.ramdantimetable.ramadantimetable.databinding.RowScheduleListBinding
import com.marmutech.ramdantimetable.ramadantimetable.model.TimeTableDay

class ScheduleAdapter(private var clickCallBack: ((TimeTableDay) -> Unit)) :
    RecyclerView.Adapter<ScheduleViewHolder>() {

    private val mScheduleList = mutableListOf<TimeTableDay>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder {
        val binding: RowScheduleListBinding = RowScheduleListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ScheduleViewHolder(binding, clickCallBack)
    }

    override fun getItemCount() = mScheduleList.size


    override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {
        holder.bind(mScheduleList[position])
    }

    fun setScheduleList(scheduleList: List<TimeTableDay>) {
        val result: DiffUtil.DiffResult = calculateDiff(object : DiffUtil.Callback() {
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                val mLocalScheduleList = mScheduleList
                return mLocalScheduleList.get(oldItemPosition).id == scheduleList.get(
                    newItemPosition
                ).id
            }

            override fun getOldListSize(): Int {
                val mLocalScheduleList = mScheduleList
                return mLocalScheduleList.size
            }

            override fun getNewListSize() = scheduleList.size

            override fun areContentsTheSame(
                oldItemPosition: Int,
                newItemPosition: Int
            ): Boolean {
                val mLocalScheduleList = mScheduleList

                val oldObj: TimeTableDay =
                    mLocalScheduleList[oldItemPosition]

                val newObj: TimeTableDay = scheduleList[newItemPosition]

                return oldObj.countryId == newObj.countryId
                        && oldObj.day == newObj.day


            }
        })
        mScheduleList.addAll(scheduleList)
        result.dispatchUpdatesTo(this)
    }
}
