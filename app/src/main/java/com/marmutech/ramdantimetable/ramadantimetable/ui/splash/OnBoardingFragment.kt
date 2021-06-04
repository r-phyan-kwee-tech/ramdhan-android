package com.marmutech.ramdantimetable.ramadantimetable.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.marmutech.ramdantimetable.ramadantimetable.databinding.FragmentOnBoardingBinding
import com.marmutech.ramdantimetable.ramadantimetable.ui.CoreFragment
import timber.log.Timber

class OnBoardingFragment : CoreFragment() {

    private var _binding: FragmentOnBoardingBinding? = null
    private val binding: FragmentOnBoardingBinding get() = _binding!!

    private lateinit var onBoardAdapter: OnBoardAdapter

    private var previousPagePosition = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentOnBoardingBinding.inflate(inflater, container, false)
        prepareViewPager()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        attachPageListener()
    }

    override fun onDestroyView() {
        binding.viewPager.unregisterOnPageChangeCallback(onPageCallback)
        super.onDestroyView()
    }

    private fun prepareViewPager() {
        val fragments = createOnBoardFragments()
        onBoardAdapter = OnBoardAdapter(this, fragments)
        binding.viewPager.adapter = onBoardAdapter
    }

    private fun createOnBoardFragments(): List<Fragment> = listOf(
        LandingFragment(),
        FontSelectionFragment(),
        CountryStateSelectionFragment()
    )

    private fun attachPageListener() {
        binding.viewPager.registerOnPageChangeCallback(onPageCallback)
    }

    private fun startNextToDoneAnime() {
        Timber.d("startNextToDoneAnime")
    }

    private fun startDoneToNextAnime() {
        Timber.d("startDoneToNextAnime")
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    private val onPageCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            when {
                position > previousPagePosition && position == onBoardAdapter.getPageSize() - 1 -> startNextToDoneAnime()
                position < previousPagePosition && position == onBoardAdapter.getPageSize() - 2 -> startDoneToNextAnime()
            }
            previousPagePosition = position
        }
    }

    companion object {
        fun newInstance(): OnBoardingFragment {
            return OnBoardingFragment()
        }
    }
}
