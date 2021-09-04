package com.marmutech.ramdantimetable.ramadantimetable.ui.schedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.marmutech.ramdantimetable.ramadantimetable.R
import com.marmutech.ramdantimetable.ramadantimetable.databinding.FragmentScheduleListBinding
import com.marmutech.ramdantimetable.ramadantimetable.model.TimeTableDay
import com.marmutech.ramdantimetable.ramadantimetable.ui.CoreFragment
import javax.inject.Inject

class ScheduleFragment private constructor() : CoreFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private var _binding: FragmentScheduleListBinding? = null
    private val binding get() = _binding!!

    private lateinit var scheduleAdapter: ScheduleAdapter

    private val vm by viewModels<ScheduleViewModel> { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentScheduleListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpToolBar()
        prepareUi()
        observeUi()
        attachViewListener()
        vm.onViewCreated()
    }

    private fun setUpToolBar() {
        binding.toolBar.inflateMenu(R.menu.menu_schedule_list)
    }

    private fun attachViewListener() {
        binding.toolBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_info -> {
                    infoSheet()
                    return@setOnMenuItemClickListener true
                }
                else -> return@setOnMenuItemClickListener false
            }
        }
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
            bindToolBarTitle(it.toolBarTitle)
            it.days?.let { days ->
                bindListData(days)
            }
        })
    }

    private fun bindToolBarTitle(toolBarTitle: String) {
        binding.toolBar.title =
            if (toolBarTitle.isNotEmpty()) toolBarTitle else getString(R.string.app_name)
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

    private fun infoSheet() {
        val bottomSheetFragment = InfoBottomSheetFragment()
        bottomSheetFragment.show(parentFragmentManager, InfoBottomSheetFragment.TAG)
    }

    companion object {
        fun newInstance() = ScheduleFragment()
    }
}
