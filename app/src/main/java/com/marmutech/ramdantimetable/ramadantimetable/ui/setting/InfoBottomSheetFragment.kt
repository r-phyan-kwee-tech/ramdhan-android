package com.marmutech.ramdantimetable.ramadantimetable.ui.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.marmutech.ramdantimetable.ramadantimetable.R
import com.marmutech.ramdantimetable.ramadantimetable.databinding.FragmentInfoBottomSheetBinding
import com.marmutech.ramdantimetable.ramadantimetable.ui.CoreBottomSheetFragment
import com.marmutech.ramdantimetable.ramadantimetable.ui.MainViewModel
import com.marmutech.ramdantimetable.ramadantimetable.ui.ScreenType
import javax.inject.Inject

class InfoBottomSheetFragment private constructor() : CoreBottomSheetFragment(),
    View.OnClickListener {

    private var _binding: FragmentInfoBottomSheetBinding? = null
    private val binding: FragmentInfoBottomSheetBinding get() = _binding!!

    @Inject
    lateinit var vmFactory: ViewModelProvider.Factory

    private val navigation by activityViewModels<MainViewModel> { vmFactory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentInfoBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        attachClickListener()
    }

    private fun attachClickListener() {
        with(binding) {
            tvLocation.setOnClickListener(this@InfoBottomSheetFragment)
            tvCredits.setOnClickListener(this@InfoBottomSheetFragment)
            tvLicense.setOnClickListener(this@InfoBottomSheetFragment)
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.tvLocation -> showInfoFragments(ScreenType.ChangeLocation)
            R.id.tvCredits -> showInfoFragments(ScreenType.CreditScreen)
            R.id.tvLicense -> showInfoFragments(ScreenType.LicenseScreen)
        }
    }

    private fun showInfoFragments(screenType: ScreenType) {
        dismiss()
        navigation.goTo(screenType)
    }

    companion object {

        private const val TAG: String = "tag_info_bottom_sheet"

        fun openInfoSheetFragment(fragmentManager: FragmentManager) {
            val bottomSheetFragment = InfoBottomSheetFragment()
            bottomSheetFragment.show(fragmentManager, TAG)
        }
    }
}
