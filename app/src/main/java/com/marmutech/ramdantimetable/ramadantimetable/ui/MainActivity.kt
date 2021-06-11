package com.marmutech.ramdantimetable.ramadantimetable.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.marmutech.ramdantimetable.ramadantimetable.R
import com.marmutech.ramdantimetable.ramadantimetable.databinding.ActivityMainBinding
import com.marmutech.ramdantimetable.ramadantimetable.ui.splash.OnBoardingFragment
import javax.inject.Inject

class MainActivity : CoreActivity() {

    @Inject
    lateinit var vmFactory: ViewModelProvider.Factory

    private lateinit var binding: ActivityMainBinding

    private val vm: MainViewModel by lazy {
        ViewModelProvider(this, vmFactory)[MainViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observeData()
        vm.onCreate()
    }

    private fun observeData() {
        vm.mainUiModel.observe(this, Observer { uiModel ->
            uiModel?.let {
                handleScreen(it.openScreen)
            }
        })
    }

    private fun handleScreen(openScreen: ScreenType) {
        when (openScreen) {
            ScreenType.SplashScreen -> inflateFragment(OnBoardingFragment.newInstance())
            ScreenType.ListScreen -> TODO()
            ScreenType.Detail -> TODO()
            ScreenType.Screen -> TODO()
            ScreenType.SettingScreen -> TODO()
        }
    }

    private fun inflateFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.mainContainerView, fragment).commit()
    }
}
