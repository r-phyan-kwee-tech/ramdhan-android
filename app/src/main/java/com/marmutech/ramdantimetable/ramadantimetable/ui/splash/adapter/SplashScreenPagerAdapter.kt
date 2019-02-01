package com.marmutech.ramdantimetable.ramadantimetable.ui.splash.adapter

import androidx.fragment.app.FragmentPagerAdapter
import com.marmutech.ramdantimetable.ramadantimetable.ui.splash.CountryStateSelectionFragment
import com.marmutech.ramdantimetable.ramadantimetable.ui.splash.FontSelectionFragment
import com.marmutech.ramdantimetable.ramadantimetable.ui.splash.LandingFragment


class SplashScreenPagerAdapter(fm: androidx.fragment.app.FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): androidx.fragment.app.Fragment? {

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