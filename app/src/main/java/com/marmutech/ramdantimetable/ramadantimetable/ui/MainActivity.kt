package com.marmutech.ramdantimetable.ramadantimetable.ui

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.marmutech.ramdantimetable.ramadantimetable.databinding.ActivityMainBinding
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

    private fun handleScreen(openScreen: ScreenType){
        when(openScreen){
            ScreenType.SplashScreen -> TODO()
            ScreenType.ListScreen -> TODO()
            ScreenType.Detail -> TODO()
            ScreenType.Screen -> TODO()
            ScreenType.SettingScreen -> TODO()
        }
    }
}
