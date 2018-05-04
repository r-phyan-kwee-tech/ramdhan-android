package com.marmutech.ramdantimetable.ramadantimetable.ui.detail

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.CollapsingToolbarLayout
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.marmutech.ramdantimetable.ramadantimetable.AppExecutors
import com.marmutech.ramdantimetable.ramadantimetable.R
import com.marmutech.ramdantimetable.ramadantimetable.ui.detail.duapager.ViewPagerAdapter
import timber.log.Timber
import javax.inject.Inject

class DetailActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var appExecutors: AppExecutors

    private lateinit var detailViewModel: DetailViewModel

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
        collapsingToolbar?.setExpandedTitleColor(resources.getColor(android.R.color.transparent))


        //ViewModel Class Declaration
        detailViewModel = ViewModelProviders.of(this, viewModelFactory).get(DetailViewModel::class.java)

        detailViewModel.loadDay("0ad22a7865a54b4dbd5bf35a1a81f7ac")


        detailViewModel.timeTableDay.observe(this, Observer { dayResource ->
            //TODO bind data from repo here
            print(dayResource?.data)
            Timber.d("DAY_RESPONSE", dayResource?.data)


        })
    }


}
