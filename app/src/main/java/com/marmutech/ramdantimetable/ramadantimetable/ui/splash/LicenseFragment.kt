package com.marmutech.ramdantimetable.ramadantimetable.ui.splash


import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.marmutech.ramdantimetable.ramadantimetable.R
import com.marmutech.ramdantimetable.ramadantimetable.databinding.FragmentLicenseBinding
import com.marmutech.ramdantimetable.ramadantimetable.di.Injectable

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class LicenseFragment : Fragment(), Injectable {

    var binding:FragmentLicenseBinding? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_license, container, false)
        var wvLicense = binding?.wvLicense
        wvLicense!!.loadUrl("file:///android_asset/licenses.html")
        return binding?.root
    }


}
