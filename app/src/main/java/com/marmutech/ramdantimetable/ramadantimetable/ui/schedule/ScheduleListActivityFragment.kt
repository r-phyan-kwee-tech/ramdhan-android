package com.marmutech.ramdantimetable.ramadantimetable.ui.schedule

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
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
import com.marmutech.ramdantimetable.ramadantimetable.di.Injectable
import com.marmutech.ramdantimetable.ramadantimetable.model.TimeTableDay
import com.marmutech.ramdantimetable.ramadantimetable.vo.Resource
import timber.log.Timber
import javax.inject.Inject


/**
 * A placeholder fragment containing a simple view.
 */
class ScheduleListActivityFragment : Fragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var scheduleViewModel: ScheduleViewModel

    private var binding: FragmentScheduleListActivityBinding? = null
    private var scheduleAdapter: ScheduleAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_schedule_list_activity, container, false)

        setUpRecycler()

        return binding?.root
    }

    private fun setUpRecycler() {
        scheduleAdapter = ScheduleAdapter(scheduleClickCallBack)
        binding?.rvScheduleList?.layoutManager = LinearLayoutManager(context)
        binding?.rvScheduleList?.adapter = scheduleAdapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val viewModel = ViewModelProviders.of(this, viewModelFactory).get(ScheduleViewModel::class.java)

        subscribeUi(viewModel)

    }

    private val scheduleClickCallBack: ScheduleClickCallBack = object : ScheduleClickCallBack {
        override fun onClick(timeTableDay: TimeTableDay) {
            if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
                (activity as ScheduleListActivity).goToDetail(timeTableDay)
            }
        }

    }

    private fun subscribeUi(viewModel: ScheduleViewModel) {
        viewModel.loadTimetableDayList("0b60dd4d4a7841808c94764e716e29af", 10, 1)
        viewModel.daysList.observe(this, Observer<Resource<List<TimeTableDay>>> { t ->
            Timber.d("dayList obersve " + t?.data)
            if(t?.data !=null){
                binding?.isLoading=false
                scheduleAdapter?.setScheduleList(t.data)
            }else{
                binding?.isLoading=true
            }
            binding?.executePendingBindings()

        })

    }
}
