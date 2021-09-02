package com.marmutech.ramdantimetable.ramadantimetable.ui.schedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.marmutech.ramdantimetable.ramadantimetable.databinding.FragmentScheduleListActivityBinding
import com.marmutech.ramdantimetable.ramadantimetable.model.TimeTableDay
import com.marmutech.ramdantimetable.ramadantimetable.ui.CoreFragment
import javax.inject.Inject

class ScheduleFragment private constructor() : CoreFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private var _binding: FragmentScheduleListActivityBinding? = null
    private val binding get() = _binding!!

    private lateinit var scheduleAdapter: ScheduleAdapter

    private val vm by viewModels<ScheduleViewModel> { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentScheduleListActivityBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareUi()
        observeUi()
        vm.onViewCreated()
    }

    private fun prepareUi() {
        scheduleAdapter = ScheduleAdapter(object : ScheduleClickCallBack {
            override fun onClick(timeTableDay: TimeTableDay) {

            }
        })
        with(binding.rvScheduleList) {
            layoutManager = LinearLayoutManager(context)
            adapter = scheduleAdapter
        }
    }

    private fun observeUi() {
        vm.uiModel.observe(viewLifecycleOwner, {
            bindLoading(it.loading)
            bindIsEid(it.isEid)
            it.days?.let { days ->
                bindListData(days)
            }
        })
    }

    private fun bindLoading(loading: Boolean) {
        binding.pbLoading.visibility = if (loading) View.VISIBLE else View.GONE
    }

    private fun bindListData(list: List<TimeTableDay>) {
        scheduleAdapter.setScheduleList(list)
    }

    private fun bindIsEid(isEid: Boolean) {
        if (isEid) {
            showEid()
        } else {
            hideEid()
        }

    }

    private fun showEid() {
        with(binding) {
            imageView.visibility = View.VISIBLE
            tvEidWish.visibility = View.VISIBLE
        }
    }

    private fun hideEid() {
        with(binding) {
            imageView.visibility = View.GONE
            tvEidWish.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = ScheduleFragment()
    }
}
