package com.marmutech.ramdantimetable.ramadantimetable.ui.splash


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SwitchCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.marmutech.ramdantimetable.ramadantimetable.databinding.FragmentFontSelectionBinding
import com.marmutech.ramdantimetable.ramadantimetable.ui.CoreFragment
import com.marmutech.ramdantimetable.ramadantimetable.util.CommonUtil
import javax.inject.Inject

class FontSelectionFragment : CoreFragment() {

    @Inject
    lateinit var commonUtil: CommonUtil

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var legacySplashViewModel: LegacySplashViewModel
    private lateinit var vm: FontSelectionViewModel

    private var binding: FragmentFontSelectionBinding? = null
    private var fontSwitch: SwitchCompat? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFontSelectionBinding.inflate(inflater, container, false)
        fontSwitch = binding?.swChangeType

        fontSwitch?.setOnCheckedChangeListener { _, isChecked -> vm.setEnableUnicode(isChecked) }
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        legacySplashViewModel = ViewModelProvider(
            this,
            viewModelFactory
        ).get(LegacySplashViewModel::class.java)
        vm = ViewModelProvider(this, viewModelFactory)[FontSelectionViewModel::class.java]

        observeData()

        vm.onViewCreated()
    }

    private fun observeData() {
        vm.isEnableUnicode.observe(viewLifecycleOwner, Observer {
            fontSwitch?.isChecked = it
        })
    }
}
