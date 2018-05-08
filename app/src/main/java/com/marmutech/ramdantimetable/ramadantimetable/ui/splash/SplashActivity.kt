package com.marmutech.ramdantimetable.ramadantimetable.ui.splash

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.app.AppCompatDelegate
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
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

    var tvNext: ImageView? = null
    var tvPrev: ImageView? = null
    var tvFinish: TextView? = null

    //TODO proper DataBinding  has to apply
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        imageViewPrelolipop()
        if (userPref.isSplashFinished()) {
            val intent: Intent = Intent(this, ScheduleListActivity::class.java)
            startActivity(intent)

            finish()
        }
        introViewPager = findViewById(R.id.help_viewpager)
        pageIndicator = findViewById(R.id.help_viewpager_indicator)
        tvNext = findViewById(R.id.help_tv_next)
        tvPrev = findViewById(R.id.help_tv_back)
        tvFinish = findViewById(R.id.tvFinish)
        tvPrev?.visibility = GONE
        pagerButtonUIUpdate(0)

        screenSlidePagerAdapter = SplashScreenPagerAdapter(supportFragmentManager)


        introViewPager?.adapter = screenSlidePagerAdapter
        pageIndicator?.setViewPager(introViewPager)


        tvPrev?.setOnClickListener(this)
        tvNext?.setOnClickListener(this)
        tvFinish?.setOnClickListener(this)

        pageIndicator?.strokeWidth = 2F
        userPref.getStateId()

        changePagerScroller()
        introViewPager?.addOnPageChangeListener(this)

        Log.e("PREF", userPref.getStateId())
    }

    @RequiresApi
    fun imageViewPrelolipop() {
        if (Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP) {
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        }


    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onClick(p0: View?) {

        if (p0?.tag as Int == 111) {
            val intent: Intent = Intent(this, ScheduleListActivity::class.java)
            startActivity(intent)
            userPref.setSplashFinished(true)
            finish()
        } else {
            introViewPager?.setCurrentItem(p0?.tag as Int, true)

        }

    }


    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    fun pagerButtonUIUpdate(position: Int) {
        when (position) {
            0 -> {
                tvFinish?.visibility = GONE
                tvNext?.visibility = VISIBLE
                tvNext?.tag = 1
                tvPrev?.visibility = GONE


            }
            1 -> {
                tvNext?.tag = 2
                tvNext?.visibility = VISIBLE
                tvPrev?.visibility = VISIBLE
                tvFinish?.visibility = GONE
                tvPrev?.tag = 0
            }
            2 -> {

                tvFinish?.visibility = VISIBLE
                tvNext?.visibility = GONE
                tvFinish?.tag = 111
                tvPrev?.visibility = VISIBLE
                tvPrev?.tag = 1


            }

        }
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onPageScrollStateChanged(state: Int) {
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
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
