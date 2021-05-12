package com.marmutech.ramdantimetable.ramadantimetable.ui.setting

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.marmutech.ramdantimetable.ramadantimetable.R
import com.marmutech.ramdantimetable.ramadantimetable.databinding.FragmentCreditBinding
import com.marmutech.ramdantimetable.ramadantimetable.ui.CoreFragment


class CreditFragment : CoreFragment() {
    var binding: FragmentCreditBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_credit, container, false)

        return binding?.root
    }

    override fun onStart() {
        super.onStart()
        binding?.versionNumber = getAppVersion(requireContext())
    }

    /**
     * @return Application's version code from the `PackageManager`.
     */
    fun getAppVersion(context: Context): String {
        try {


            val packageInfo = context.packageManager
                    .getPackageInfo(context.packageName, 0)
            return packageInfo.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            // should never happen
            throw RuntimeException("Could not get package name: " + e)
        }

    }
}
