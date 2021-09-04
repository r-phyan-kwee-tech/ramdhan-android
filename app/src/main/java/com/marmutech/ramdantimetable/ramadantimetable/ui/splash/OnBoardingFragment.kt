package com.marmutech.ramdantimetable.ramadantimetable.ui.splash


import android.graphics.drawable.Animatable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.marmutech.ramdantimetable.ramadantimetable.R
import com.marmutech.ramdantimetable.ramadantimetable.databinding.FragmentOnBoardingBinding
import com.marmutech.ramdantimetable.ramadantimetable.ui.CoreFragment
import com.marmutech.ramdantimetable.ramadantimetable.ui.MainViewModel
import com.marmutech.ramdantimetable.ramadantimetable.ui.ScreenType
import timber.log.Timber
import javax.inject.Inject

class OnBoardingFragment : CoreFragment() {

    private var _binding: FragmentOnBoardingBinding? = null
    private val binding: FragmentOnBoardingBinding get() = _binding!!

    private lateinit var onBoardAdapter: OnBoardAdapter

    private var previousPagePosition = 0

    private var forwardToTickAvd: AnimatedVectorDrawableCompat? = null
    private var tickToForwardAvd: AnimatedVectorDrawableCompat? = null

    @Inject
    lateinit var vmFactory: ViewModelProvider.Factory

    private val vm: SplashViewModel by lazy {
        ViewModelProvider(requireActivity(), vmFactory)[SplashViewModel::class.java]
    }

    private val vmMain: MainViewModel by lazy {
        ViewModelProvider(requireActivity(), vmFactory)[MainViewModel::class.java]
    }

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
        observeData()
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
            Timber.d("nav value ${vmMain.mainUiModel.value?.openScreen}")
            vm.onNextClick()
        }
        binding.backImageButton.setOnClickListener {
            vm.onPrevClick()
        }
    }

    private fun observeData() {
        vm.movePageByPosition.observe(viewLifecycleOwner, { position ->
            position?.let {
                binding.viewPager.currentItem = it
            }
        })
        vm.onBoardUiModel.observe(viewLifecycleOwner, { uiModel ->
            uiModel?.let {
                binding.backImageButton.visibility = if (it.showPrevButton) View.VISIBLE else View.GONE
            }
        })
        vm.openScheduleList.observe(viewLifecycleOwner, {
            if (it) {
                vmMain.goTo(ScreenType.ListScreen)
            }
        })
    }

    private fun prepareViewPager() {
        val fragments = createOnBoardFragments()
        onBoardAdapter = OnBoardAdapter(this, fragments)
        binding.viewPager.adapter = onBoardAdapter
        TabLayoutMediator(binding.indicatorTabLayout, binding.viewPager) { _, _ -> }.attach()
        vm.setTotalPageCount(fragments.size)
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
