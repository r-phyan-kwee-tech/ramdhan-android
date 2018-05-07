package com.marmutech.ramdantimetable.ramadantimetable.ui.detail

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.CollapsingToolbarLayout
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.marmutech.ramdantimetable.ramadantimetable.R
import com.marmutech.ramdantimetable.ramadantimetable.databinding.ActivityDetailBinding
import com.marmutech.ramdantimetable.ramadantimetable.model.TimeTableDay
import com.marmutech.ramdantimetable.ramadantimetable.ui.detail.duapager.ViewPagerAdapter
import com.marmutech.ramdantimetable.ramadantimetable.util.UserPrefUtil
import javax.inject.Inject

class DetailActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var prefUtil: UserPrefUtil
    private lateinit var detailViewModel: DetailViewModel
    private var dayId: String? = null

    var tabLayout: TabLayout? = null
    var pageAdapter: ViewPagerAdapter? = null
    var viewPager: ViewPager? = null
    var toolbar: Toolbar? = null
    var collapsingToolbar: CollapsingToolbarLayout? = null

    var binding: ActivityDetailBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)

        toolbar = binding?.toolbar
        toolbar?.setTitleTextColor(resources.getColor(R.color.colorAccent))
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        collapsingToolbar = binding?.collapsingToolbar
        tabLayout = binding?.tabs
        viewPager = binding?.viewpager
        pageAdapter = ViewPagerAdapter(supportFragmentManager, arrayOf("ဒိုအာ", "EN", "دُعَاء\u200E"))
        viewPager?.adapter = pageAdapter
        tabLayout?.setupWithViewPager(viewPager)
        collapsingToolbar?.setExpandedTitleColor(resources.getColor(android.R.color.transparent))


        //Fetching data



        //ViewModel Class Declaration
        detailViewModel = ViewModelProviders.of(this, viewModelFactory).get(DetailViewModel::class.java)

        if (intent.data != null) {
            var data: Uri = intent.data
            dayId = data!!.getQueryParameter("dayId")
            detailViewModel.loadDay(dayId)
        }

        binding?.isUnicode = prefUtil.getFont()
        detailViewModel.timeTableDay.observe(this, Observer { dayResource ->
            //TODO bind data from repo here
            if (dayResource?.data != null) {
                binding?.timetable = dayResource?.data
            }


        })
    }


}
