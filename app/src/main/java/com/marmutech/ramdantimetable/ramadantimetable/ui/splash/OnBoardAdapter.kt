package com.marmutech.ramdantimetable.ramadantimetable.ui.splash

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class OnBoardAdapter(hostFragment: Fragment, private val fragmentList: List<Fragment>) :
    FragmentStateAdapter(hostFragment) {
    override fun getItemCount(): Int = getPageSize()

    override fun createFragment(position: Int): Fragment = fragmentList[position]

    fun getPageSize() = fragmentList.size
}
