package com.marmutech.ramdantimetable.ramadantimetable.ui.splash


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.marmutech.ramdantimetable.ramadantimetable.databinding.FragmentLicenseBinding
import com.marmutech.ramdantimetable.ramadantimetable.ui.CoreFragment

class LicenseFragment : CoreFragment() {

    private var _binding: FragmentLicenseBinding? = null
    private val binding: FragmentLicenseBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLicenseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.wvLicense.loadUrl("file:///android_asset/licenses.html")
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
