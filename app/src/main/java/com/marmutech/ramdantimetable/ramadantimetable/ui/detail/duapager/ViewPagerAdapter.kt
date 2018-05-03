package com.marmutech.ramdantimetable.ramadantimetable.ui.detail.duapager

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.app.FragmentTransaction

class ViewPagerAdapter(internal var fragManager: FragmentManager, internal var mtitles: Array<String>) : FragmentPagerAdapter(fragManager) {
    internal var ft: FragmentTransaction

    init {
        this.ft = fragManager.beginTransaction()
    }

    override fun getItem(position: Int): Fragment? {
        // getItem is called to instantiate the fragment for the given page.
        when (position) {
            0 -> return DuaInfoFragment.newInstance(position.toString(), position.toString())
            1 -> return DuaInfoFragment()
            2 -> return DuaInfoFragment()
        }
        return null

    }

    override fun getCount(): Int {
        return mtitles.size
    }

    override fun getPageTitle(position: Int): CharSequence? {

        return mtitles[position]
    }
}
