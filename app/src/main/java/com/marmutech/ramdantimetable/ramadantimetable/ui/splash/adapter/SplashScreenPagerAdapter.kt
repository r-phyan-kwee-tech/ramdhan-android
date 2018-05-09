package com.marmutech.ramdantimetable.ramadantimetable.ui.splash.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.marmutech.ramdantimetable.ramadantimetable.ui.splash.CountryStateSelectionFragment
import com.marmutech.ramdantimetable.ramadantimetable.ui.splash.FontSelectionFragment
import com.marmutech.ramdantimetable.ramadantimetable.ui.splash.LandingFragment
import com.marmutech.ramdantimetable.ramadantimetable.ui.splash.LicenseFragment


class SplashScreenPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment? {

        when (position) {
            0 -> return LandingFragment()
            1 -> return FontSelectionFragment()
            2 -> return CountryStateSelectionFragment()
        }
        return null

    }

    override fun getCount(): Int {
        return 3
    }
}