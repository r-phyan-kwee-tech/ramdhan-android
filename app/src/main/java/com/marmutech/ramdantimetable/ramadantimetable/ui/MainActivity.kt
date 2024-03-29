package com.marmutech.ramdantimetable.ramadantimetable.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.marmutech.ramdantimetable.ramadantimetable.R
import com.marmutech.ramdantimetable.ramadantimetable.databinding.ActivityMainBinding
import com.marmutech.ramdantimetable.ramadantimetable.ui.detail.DetailViewFragment
import com.marmutech.ramdantimetable.ramadantimetable.ui.schedule.ScheduleFragment
import com.marmutech.ramdantimetable.ramadantimetable.ui.setting.CreditFragment
import com.marmutech.ramdantimetable.ramadantimetable.ui.splash.CountryStateSelectionFragment
import com.marmutech.ramdantimetable.ramadantimetable.ui.splash.LicenseFragment
import com.marmutech.ramdantimetable.ramadantimetable.ui.splash.OnBoardingFragment
import javax.inject.Inject

class MainActivity : CoreActivity() {

    @Inject
    lateinit var vmFactory: ViewModelProvider.Factory

    private lateinit var binding: ActivityMainBinding

    private val vm: MainViewModel by viewModels { vmFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        observeData()
        vm.onCreate()
    }

    private fun observeData() {
        vm.mainUiModel.observe(this, { uiModel ->
            handleScreen(uiModel.openScreen)
        })
    }

    private fun handleScreen(openScreen: ScreenType) {
        when (openScreen) {
            is ScreenType.SplashScreen -> inflateFragment(
                OnBoardingFragment.newInstance(),
                OnBoardingFragment.tag
            )
            is ScreenType.ListScreen -> inflateFragment(
                ScheduleFragment.newInstance(),
                ScheduleFragment.tag, true
            )
            is ScreenType.DetailScreen -> inflateFragment(
                DetailViewFragment.newInstance(openScreen.detailParam),
                DetailViewFragment.tag
            )
            ScreenType.ChangeLocation -> inflateFragment(CountryStateSelectionFragment(), "tag")
            ScreenType.CreditScreen -> inflateFragment(CreditFragment(), "tag")
            ScreenType.LicenseScreen -> inflateFragment(LicenseFragment(), "tag")
        }
    }

    private fun inflateFragment(fragment: Fragment, tag: String, isRootRoute: Boolean = false) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.mainContainerView, fragment)
            .apply {
                if (!isRootRoute) addToBackStack(tag)
            }.commit()
    }
}
