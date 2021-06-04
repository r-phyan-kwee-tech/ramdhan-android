package com.marmutech.ramdantimetable.ramadantimetable.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.marmutech.ramdantimetable.ramadantimetable.databinding.FragmentOnBoardingBinding
import com.marmutech.ramdantimetable.ramadantimetable.ui.CoreFragment

class OnBoardingFragment : CoreFragment() {

    private var _binding: FragmentOnBoardingBinding? = null
    private val binding: FragmentOnBoardingBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentOnBoardingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    companion object {
        fun newInstance(): OnBoardingFragment {
            return OnBoardingFragment()
        }
    }
}
