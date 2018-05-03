package com.marmutech.ramdantimetable.ramadantimetable.ui.detail

import android.os.Bundle
import android.support.design.widget.CollapsingToolbarLayout
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.marmutech.ramdantimetable.ramadantimetable.R
import com.marmutech.ramdantimetable.ramadantimetable.ui.detail.duapager.ViewPagerAdapter

class DetailActivity : AppCompatActivity() {
    var tabLayout: TabLayout? = null
    var pageAdapter: ViewPagerAdapter? = null
    var viewPager: ViewPager? = null
    var toolbar: Toolbar? = null
    var collapsingToolbar: CollapsingToolbarLayout? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        toolbar = findViewById(R.id.toolbar)
        toolbar?.setTitleTextColor(resources.getColor(R.color.colorAccent))
        setSupportActionBar(toolbar)


        collapsingToolbar = findViewById(R.id.collapsing_toolbar)
        tabLayout = findViewById(R.id.tabs)
        viewPager = findViewById(R.id.viewpager)
        pageAdapter = ViewPagerAdapter(supportFragmentManager, arrayOf("ဒိုအာ", "Dua", "دُعَاء\u200E"))
        viewPager?.adapter = pageAdapter
        tabLayout?.setupWithViewPager(viewPager)
        collapsingToolbar?.setExpandedTitleColor(resources.getColor(android.R.color.transparent));


    }

    fun changeToolbarColor(collapsingToolbar: CollapsingToolbarLayout) {

    }
}
