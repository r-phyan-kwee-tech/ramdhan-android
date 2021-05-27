package com.marmutech.ramdantimetable.ramadantimetable.ui.splash


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SwitchCompat
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

    private val splashViewModel by lazy {
        ViewModelProvider(requireActivity(), viewModelFactory)[SplashViewModel::class.java]
    }

    private var binding: FragmentFontSelectionBinding? = null
    private var fontSwitch: SwitchCompat? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFontSelectionBinding.inflate(inflater, container, false)
        fontSwitch = binding?.swChangeType

        fontSwitch?.setOnCheckedChangeListener { _, isChecked ->
            splashViewModel.setEnableUnicode(
                isChecked
            )
        }
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        splashViewModel.onViewCreated()
    }

    private fun observeData() {
        splashViewModel.fontSelectionUiModel.observe(viewLifecycleOwner, { uiModel ->
            uiModel?.let {
                fontSwitch?.isChecked = it.isUnicodeEnable
            }
        })
    }
}
