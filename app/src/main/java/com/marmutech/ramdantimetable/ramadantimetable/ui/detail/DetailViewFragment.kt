package com.marmutech.ramdantimetable.ramadantimetable.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.marmutech.ramdantimetable.ramadantimetable.databinding.FragmentDetailViewBinding
import com.marmutech.ramdantimetable.ramadantimetable.model.TimeTableDay
import com.marmutech.ramdantimetable.ramadantimetable.ui.CoreFragment
import javax.inject.Inject

class DetailViewFragment private constructor() : CoreFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private var _binding: FragmentDetailViewBinding? = null
    private val binding: FragmentDetailViewBinding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentDetailViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        private const val KEY_TIME_TABLE_OBJECT = "key_time_table_object"

        val tag = DetailViewFragment::class.java.simpleName

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
