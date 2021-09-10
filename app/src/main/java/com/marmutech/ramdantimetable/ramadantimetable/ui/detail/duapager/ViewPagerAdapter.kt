package com.marmutech.ramdantimetable.ramadantimetable.ui.detail.duapager

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.marmutech.ramdantimetable.ramadantimetable.ui.detail.DuaTranslation

class ViewPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    private val _translatedDua = mutableListOf<DuaTranslation>()
    val data: List<DuaTranslation> get() = _translatedDua

    override fun getItemCount(): Int = _translatedDua.size

    override fun createFragment(position: Int) = DuaInfoFragment.newInstance(
        _translatedDua[position].languageCode,
        _translatedDua[position].translation
    )

    fun setData(data: List<DuaTranslation>) {
        this._translatedDua.addAll(data)
        notifyDataSetChanged()
    }
}
