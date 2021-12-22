package com.marmutech.ramdantimetable.ramadantimetable.ui.schedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.marmutech.ramdantimetable.ramadantimetable.R
import com.marmutech.ramdantimetable.ramadantimetable.databinding.FragmentScheduleListBinding
import com.marmutech.ramdantimetable.ramadantimetable.model.TimeTableDay
import com.marmutech.ramdantimetable.ramadantimetable.ui.CoreFragment
import com.marmutech.ramdantimetable.ramadantimetable.ui.MainViewModel
import com.marmutech.ramdantimetable.ramadantimetable.ui.ScreenType
import com.marmutech.ramdantimetable.ramadantimetable.ui.setting.InfoBottomSheetFragment
import javax.inject.Inject

class ScheduleFragment private constructor() : CoreFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private var _binding: FragmentScheduleListBinding? = null
    private val binding get() = _binding!!

    private lateinit var scheduleAdapter: ScheduleAdapter

    private val vm by viewModels<ScheduleViewModel> { viewModelFactory }
    private val navigation by activityViewModels<MainViewModel> { viewModelFactory }

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
        prepareUi()
        observeUi()
        attachViewListener()
        vm.onViewCreated()
    }

    private fun attachViewListener() {
        binding.toolBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_info -> {
                    openInfoSheet()
                    return@setOnMenuItemClickListener true
                }
                else -> return@setOnMenuItemClickListener false
            }
        }
    }

    private fun prepareUi() {
        scheduleAdapter = ScheduleAdapter {
            openDetailView(it)
        }
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
            bindError(it.errorMessage)
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

    private fun bindError(@StringRes errorStringRes: Int?) {
        errorStringRes?.let {
            Snackbar.make(binding.root, it, Snackbar.LENGTH_INDEFINITE).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun openDetailView(timeTableDay: TimeTableDay) {
        navigation.goTo(ScreenType.DetailScreen(timeTableDay))
    }

    private fun openInfoSheet() {
        InfoBottomSheetFragment.openInfoSheetFragment(parentFragmentManager)
    }

    companion object {
        val tag = ScheduleFragment::class.java.simpleName
        fun newInstance() = ScheduleFragment()
    }
}
