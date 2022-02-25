package com.marmutech.ramdantimetable.ramadantimetable.ui.setting

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.marmutech.ramdantimetable.ramadantimetable.R
import com.marmutech.ramdantimetable.ramadantimetable.databinding.FragmentCreditBinding
import com.marmutech.ramdantimetable.ramadantimetable.ui.CoreFragment


class CreditFragment : CoreFragment() {

    private var _binding: FragmentCreditBinding? = null
    private val binding: FragmentCreditBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvVersion.text = getString(R.string.str_version, getAppVersion(requireContext()))
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun getAppVersion(context: Context): String {
        try {
            val packageInfo = context.packageManager
                .getPackageInfo(context.packageName, 0)
            return packageInfo.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            throw RuntimeException("Could not get package name: $e")
        }
    }
}
