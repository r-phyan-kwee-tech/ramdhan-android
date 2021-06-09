package com.marmutech.ramdantimetable.ramadantimetable.ui.schedule

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.marmutech.ramdantimetable.ramadantimetable.R
import com.marmutech.ramdantimetable.ramadantimetable.databinding.FragmentScheduleListActivityBinding
import com.marmutech.ramdantimetable.ramadantimetable.model.TimeTableDay
import com.marmutech.ramdantimetable.ramadantimetable.ui.CoreFragment
import com.marmutech.ramdantimetable.ramadantimetable.util.UserPrefUtil
import com.marmutech.ramdantimetable.ramadantimetable.vo.Resource
import timber.log.Timber
import javax.inject.Inject


/**
 * A placeholder fragment containing a simple view.
 */
class LegacyListFragment : CoreFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var prefUtil: UserPrefUtil
    var i: Int = 0

    private var binding: FragmentScheduleListActivityBinding? = null
    private var scheduleAdapter: ScheduleAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_schedule_list_activity,
            container,
            false
        )

        setUpRecycler()

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel = ViewModelProvider(this, viewModelFactory)
            .get(ScheduleViewModel::class.java)

        subscribeUi(viewModel)
    }

    override fun onResume() {
        super.onResume()
        i = 0
        val viewModel = ViewModelProvider(this, viewModelFactory)
            .get(ScheduleViewModel::class.java)

        subscribeUi(viewModel)
    }

    private fun setUpRecycler() {
        scheduleAdapter = ScheduleAdapter(scheduleClickCallBack)
        binding?.rvScheduleList?.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
        binding?.rvScheduleList?.adapter = scheduleAdapter
    }

    private val scheduleClickCallBack: ScheduleClickCallBack = object : ScheduleClickCallBack {
        override fun onClick(timeTableDay: TimeTableDay) {
            if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
                (activity as LegacyScheduleListActivity).goToDetail(timeTableDay)
            }
        }

    }

    private fun subscribeUi(viewModel: ScheduleViewModel) {
        binding?.isLoading = true
        viewModel.loadTimetableDayList(prefUtil.getStateId(), 30, 1)

        viewModel.daysList.observe(viewLifecycleOwner, Observer<Resource<List<TimeTableDay>>> { t ->
            Timber.d("dayList obersve " + t?.data)
            if (t?.data != null) {
                i++
                if (!t?.data.isEmpty()) {
                    binding?.isLoading = false
                    binding?.isListVisible = true
                    binding?.isEid = false
                    scheduleAdapter?.setScheduleList(t.data)
                }

                if (i == 4 && t.data.isEmpty()) {
                    binding?.isLoading = false
                    binding?.isListVisible = false
                    binding?.isEid = true
                }
                Log.e("FETCH_COUNT", i.toString())
                Log.e("DATA_COUNT", t.data.size.toString())

            } else {
                binding?.isLoading = true
                binding?.isEid = false


            }
            binding?.executePendingBindings()

        })

    }

}
