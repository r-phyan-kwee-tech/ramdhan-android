package com.marmutech.ramdantimetable.ramadantimetable.ui.splash


import android.content.Intent
import android.graphics.drawable.Animatable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.marmutech.ramdantimetable.ramadantimetable.R
import com.marmutech.ramdantimetable.ramadantimetable.databinding.FragmentOnBoardingBinding
import com.marmutech.ramdantimetable.ramadantimetable.ui.CoreFragment
import com.marmutech.ramdantimetable.ramadantimetable.ui.schedule.LegacyScheduleListActivity
import timber.log.Timber

class OnBoardingFragment : CoreFragment() {

    private var _binding: FragmentOnBoardingBinding? = null
    private val binding: FragmentOnBoardingBinding get() = _binding!!

    private lateinit var onBoardAdapter: OnBoardAdapter

    private var previousPagePosition = 0

    private var forwardToTickAvd: AnimatedVectorDrawableCompat? = null
    private var tickToForwardAvd: AnimatedVectorDrawableCompat? = null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentOnBoardingBinding.inflate(inflater, container, false)
        prepareViewPager()
        prepareAvd()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        attachPageListener()
        attachClickListener()
    }

    override fun onDestroyView() {
        binding.viewPager.unregisterOnPageChangeCallback(onPageCallback)
        super.onDestroyView()
    }

    private fun prepareAvd() {
        forwardToTickAvd = AnimatedVectorDrawableCompat.create(requireContext(), R.drawable.avd_arrow_forward_to_tick)
        tickToForwardAvd = AnimatedVectorDrawableCompat.create(requireContext(), R.drawable.avd_tick_to_arrow_forward)

        binding.fabNext.setImageDrawable(forwardToTickAvd)
    }

    private fun attachClickListener() {
        binding.fabNext.setOnClickListener {
            activity?.let {
                startActivity(Intent(requireContext(), LegacyScheduleListActivity::class.java))
            }
        }
    }

    private fun prepareViewPager() {
        val fragments = createOnBoardFragments()
        onBoardAdapter = OnBoardAdapter(this, fragments)
        binding.viewPager.adapter = onBoardAdapter
        TabLayoutMediator(binding.indicatorTabLayout, binding.viewPager) { _, _ -> }.attach()

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
        binding.fabNext.setImageDrawable(forwardToTickAvd)
        (binding.fabNext.drawable as Animatable).start()
    }

    private fun startDoneToNextAnime() {
        Timber.d("startDoneToNextAnime")
        binding.fabNext.setImageDrawable(tickToForwardAvd)
        (binding.fabNext.drawable as Animatable).start()
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
