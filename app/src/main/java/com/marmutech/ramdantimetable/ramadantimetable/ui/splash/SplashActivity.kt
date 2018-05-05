package com.marmutech.ramdantimetable.ramadantimetable.ui.splash

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.marmutech.ramdantimetable.ramadantimetable.R
import com.marmutech.ramdantimetable.ramadantimetable.ui.splash.adapter.SplashScreenPagerAdapter
import com.marmutech.ramdantimetable.ramadantimetable.util.UserPrefUtil
import com.viewpagerindicator.CirclePageIndicator
import javax.inject.Inject
import com.marmutech.ramdantimetable.ramadantimetable.ui.common.ViewPagerScroller
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.HasSupportFragmentInjector
import java.lang.reflect.AccessibleObject.setAccessible
import java.lang.reflect.Field


class SplashActivity : AppCompatActivity(), HasSupportFragmentInjector {


    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var userPref: UserPrefUtil


    override fun supportFragmentInjector() = dispatchingAndroidInjector

    var helpViewPager: ViewPager? = null
    var pageIndicator: CirclePageIndicator? = null
    var screenSlidePagerAdapter: SplashScreenPagerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        helpViewPager = findViewById(R.id.help_viewpager)
        pageIndicator = findViewById(R.id.help_viewpager_indicator)

        screenSlidePagerAdapter = SplashScreenPagerAdapter(supportFragmentManager)


        helpViewPager?.adapter = screenSlidePagerAdapter
        pageIndicator?.setViewPager(helpViewPager)

        pageIndicator?.strokeWidth = 2F
        userPref.getStateId()

        changePagerScroller()

        Log.e("PREF", userPref.getStateId())
    }

    private fun changePagerScroller() {
        try {
            var mScroller: Field? = null
            mScroller = ViewPager::class.java.getDeclaredField("mScroller")
            mScroller.isAccessible = true
            val scroller = ViewPagerScroller(helpViewPager!!.context)
            mScroller!!.set(helpViewPager, scroller)
        } catch (e: Exception) {
            Log.e("TAG", "error of change scroller ", e)
        }

    }
}
