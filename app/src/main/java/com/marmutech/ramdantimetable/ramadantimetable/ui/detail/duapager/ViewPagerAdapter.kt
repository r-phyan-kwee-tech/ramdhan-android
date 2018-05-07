package com.marmutech.ramdantimetable.ramadantimetable.ui.detail.duapager

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.app.FragmentTransaction
import com.marmutech.ramdantimetable.ramadantimetable.model.TimeTableDay

class ViewPagerAdapter(internal var fragManager: FragmentManager, internal var mtitles: Array<String>, timeTableDay: TimeTableDay) : FragmentPagerAdapter(fragManager) {
    internal var ft: FragmentTransaction
    internal lateinit var _timetableDay: TimeTableDay

    init {
        this.ft = fragManager.beginTransaction()
        this._timetableDay = timeTableDay
    }

    override fun getItem(position: Int): Fragment? {
        // getItem is called to instantiate the fragment for the given page.
        when (position) {
            0 -> return DuaInfoFragment.newInstance("mm", _timetableDay.duaMmUni)
            1 -> return DuaInfoFragment.newInstance("en", _timetableDay.duaEn)
            2 -> return DuaInfoFragment.newInstance("ar", _timetableDay.duaAr)
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
