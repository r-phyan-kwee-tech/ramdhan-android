package com.marmutech.ramdantimetable.ramadantimetable.ui.schedule

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.marmutech.ramdantimetable.ramadantimetable.R
import com.marmutech.ramdantimetable.ramadantimetable.databinding.FragmentScheduleListActivityBinding
import com.marmutech.ramdantimetable.ramadantimetable.model.TimeTableDay
import com.marmutech.ramdantimetable.ramadantimetable.vo.Resource
import kotlinx.android.synthetic.main.fragment_schedule_list_activity.*

/**
 * A placeholder fragment containing a simple view.
 */
class ScheduleListActivityFragment : Fragment() {

    private var binding: FragmentScheduleListActivityBinding? = null
    private var scheduleAdapter: ScheduleAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_schedule_list_activity, container, false)

        scheduleAdapter = ScheduleAdapter(scheduleClickCallBack)
        rv_schedule_list.layoutManager = LinearLayoutManager(context)
        rv_schedule_list.adapter = scheduleAdapter

        return binding?.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val viewModel = ViewModelProviders.of(this).get(ScheduleViewModel::class.java)

        subscribeUi(viewModel)

    }

    private val scheduleClickCallBack: ScheduleClickCallBack = object : ScheduleClickCallBack {
        override fun onClick(timeTableDay: TimeTableDay) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

    }

    private fun subscribeUi(viewModel: ScheduleViewModel) {
        viewModel.daysList.observe(this, Observer<Resource<List<TimeTableDay>>> { t -> scheduleAdapter?.setScheduleList(t?.data) })

    }
}
