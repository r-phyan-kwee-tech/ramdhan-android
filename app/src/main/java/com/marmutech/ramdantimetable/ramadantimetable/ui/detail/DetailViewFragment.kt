package com.marmutech.ramdantimetable.ramadantimetable.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.marmutech.ramdantimetable.ramadantimetable.R
import com.marmutech.ramdantimetable.ramadantimetable.databinding.FragmentDetailViewBinding
import com.marmutech.ramdantimetable.ramadantimetable.model.TimeTableDay
import com.marmutech.ramdantimetable.ramadantimetable.ui.CoreFragment
import com.marmutech.ramdantimetable.ramadantimetable.ui.detail.duapager.ViewPagerAdapter
import javax.inject.Inject
import kotlin.properties.Delegates

class DetailViewFragment private constructor() : CoreFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private var _binding: FragmentDetailViewBinding? = null
    private val binding: FragmentDetailViewBinding get() = _binding!!

    private val vm by viewModels<DetailViewModel>()

    private var pagerAdapter by Delegates.notNull<ViewPagerAdapter>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentDetailViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareData()
        prepareViewPager()
        observeUiData()
        attachListener()
    }

    private fun attachListener() {
        binding.toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }
    }

    private fun prepareViewPager() {
        pagerAdapter = ViewPagerAdapter(parentFragmentManager, lifecycle)
        binding.viewpager.adapter = pagerAdapter
        TabLayoutMediator(
            binding.tabs, binding.viewpager
        ) { tab, position -> generateTabTitle(tab, position, pagerAdapter.data) }.attach()
    }

    private fun generateTabTitle(tab: TabLayout.Tab, position: Int, data: List<DuaTranslation>) {
        tab.text = when (data[position].languageCode) {
            LanguageCode.EN -> getString(R.string.dua_en)
            LanguageCode.MM -> getString(R.string.dua_mm_uni)
            LanguageCode.AR -> getString(R.string.dua_ar)
        }
    }

    private fun prepareData() {
        arguments?.let {
            val data: TimeTableDay = it.getParcelable(KEY_TIME_TABLE_OBJECT)!!
            vm.onViewCreated(data)
        }
    }

    private fun observeUiData() {
        vm.uiModel.observe(viewLifecycleOwner) {
            bindHeaderData(it.headerData)
            bindPagerData(it.translatedDuaList)
        }
    }

    private fun bindPagerData(translatedDuaList: List<DuaTranslation>) {
        pagerAdapter.setData(translatedDuaList)
    }

    private fun bindHeaderData(headerData: HeaderData) {
        with(binding) {
            tvDay.text = getString(R.string.str_day, headerData.day.toString())
            tvDate.text = headerData.calenderDay
            tvSehriTime.text = headerData.sehriTime
            tvIftariTime.text = headerData.iftariTime
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        private const val KEY_TIME_TABLE_OBJECT = "key_time_table_object"

        val tag: String = DetailViewFragment::class.java.simpleName

        fun newInstance(timeTableDay: TimeTableDay): DetailViewFragment {
            val arg = Bundle()
            arg.putParcelable(KEY_TIME_TABLE_OBJECT, timeTableDay)
            val detailViewFragment = DetailViewFragment().also {
                it.arguments = arg
            }
            return detailViewFragment
        }
    }
}
