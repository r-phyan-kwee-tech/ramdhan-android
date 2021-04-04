package com.marmutech.ramdantimetable.ramadantimetable.ui.detail

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.tabs.TabLayout
import com.marmutech.ramdantimetable.ramadantimetable.R
import com.marmutech.ramdantimetable.ramadantimetable.databinding.ActivityDetailBinding
import com.marmutech.ramdantimetable.ramadantimetable.ui.detail.duapager.ViewPagerAdapter
import com.marmutech.ramdantimetable.ramadantimetable.util.UserPrefUtil
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject


class DetailActivity : AppCompatActivity(), HasAndroidInjector {

    @Inject lateinit var androidInjector : DispatchingAndroidInjector<Any>

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var prefUtil: UserPrefUtil
    private lateinit var detailViewModel: DetailViewModel
    private var dayId: String? = null

    var tabLayout: TabLayout? = null
    var pageAdapter: ViewPagerAdapter? = null
    var viewPager: androidx.viewpager.widget.ViewPager? = null
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
                supportActionBar?.title = String.format(resources.getString(R.string.str_day), dayResource?.data.day)
                pageAdapter = ViewPagerAdapter(supportFragmentManager, arrayOf(this.resources.getString(R.string.dua_mm_uni), "EN", "دُعَاء\u200E"), dayResource?.data)
                viewPager?.adapter = pageAdapter
                tabLayout?.setupWithViewPager(viewPager)

            }
        })
    }

    override fun androidInjector(): AndroidInjector<Any> = androidInjector

}
