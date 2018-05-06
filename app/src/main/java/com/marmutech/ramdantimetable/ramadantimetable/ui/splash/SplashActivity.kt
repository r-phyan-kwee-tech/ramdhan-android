package com.marmutech.ramdantimetable.ramadantimetable.ui.splash

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.AppCompatTextView
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.TextView
import com.marmutech.ramdantimetable.ramadantimetable.R
import com.marmutech.ramdantimetable.ramadantimetable.ui.common.ViewPagerScroller
import com.marmutech.ramdantimetable.ramadantimetable.ui.schedule.ScheduleListActivity
import com.marmutech.ramdantimetable.ramadantimetable.ui.splash.adapter.SplashScreenPagerAdapter
import com.marmutech.ramdantimetable.ramadantimetable.util.UserPrefUtil
import com.viewpagerindicator.CirclePageIndicator
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import java.lang.reflect.Field
import javax.inject.Inject


class SplashActivity : AppCompatActivity(), HasSupportFragmentInjector, ViewPager.OnPageChangeListener, View.OnClickListener {


    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var userPref: UserPrefUtil


    override fun supportFragmentInjector() = dispatchingAndroidInjector

    var introViewPager: ViewPager? = null
    var pageIndicator: CirclePageIndicator? = null
    var screenSlidePagerAdapter: SplashScreenPagerAdapter? = null

    var tvNext: TextView? = null
    var tvPrev: TextView? = null

    //TODO proper DataBinding  has to apply
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        introViewPager = findViewById(R.id.help_viewpager)
        pageIndicator = findViewById(R.id.help_viewpager_indicator)
        tvNext = findViewById(R.id.help_tv_next)
        tvPrev = findViewById(R.id.help_tv_back)
        tvPrev?.visibility = GONE
        pagerButtonUIUpdate(0)

        screenSlidePagerAdapter = SplashScreenPagerAdapter(supportFragmentManager)


        introViewPager?.adapter = screenSlidePagerAdapter
        pageIndicator?.setViewPager(introViewPager)


        tvPrev?.setOnClickListener(this)
        tvNext?.setOnClickListener(this)

        pageIndicator?.strokeWidth = 2F
        userPref.getStateId()

        changePagerScroller()

        Log.e("PREF", userPref.getStateId())
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onClick(p0: View?) {

        var tv: AppCompatTextView = p0 as AppCompatTextView
        if (tv.tag as Int == 111) {
            val intent: Intent = Intent(this, ScheduleListActivity::class.java)
            startActivity(intent)
        } else {

            introViewPager?.setCurrentItem(tv.tag as Int, true)
            pagerButtonUIUpdate(tv.tag as Int)

        }

    }


    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    fun pagerButtonUIUpdate(position: Int) {
        when (position) {
            0 -> {
                tvNext?.visibility = VISIBLE
                tvNext?.background = resources.getDrawable(R.drawable.ic_right_arrow)
                tvNext?.tag = 1
                tvPrev?.visibility = GONE

            }
            1 -> {
                tvNext?.tag = 2
                tvNext?.background = resources.getDrawable(R.drawable.ic_right_arrow)
                tvNext?.visibility = VISIBLE
                tvPrev?.visibility = VISIBLE
                tvPrev?.tag = 0
            }
            2 -> {
                tvNext?.tag = 3
                tvNext?.background = resources.getDrawable(R.drawable.ic_right_arrow)
                tvNext?.visibility = VISIBLE
                tvPrev?.visibility = VISIBLE
                tvPrev?.tag = 1
            }
            3 -> {
                tvNext?.background = null
                tvNext?.text = "Finish"
                tvNext?.tag = 111
                tvPrev?.visibility = VISIBLE
                tvPrev?.tag = 2
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onPageScrollStateChanged(state: Int) {
        pagerButtonUIUpdate(state)

        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        pagerButtonUIUpdate(position)
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onPageSelected(position: Int) {
        pagerButtonUIUpdate(position)
    }

    private fun changePagerScroller() {
        try {
            var mScroller: Field? = null
            mScroller = ViewPager::class.java.getDeclaredField("mScroller")
            mScroller.isAccessible = true
            val scroller = ViewPagerScroller(introViewPager!!.context)
            mScroller!!.set(introViewPager, scroller)
        } catch (e: Exception) {
            Log.e("TAG", "error of change scroller ", e)
        }

    }
}
