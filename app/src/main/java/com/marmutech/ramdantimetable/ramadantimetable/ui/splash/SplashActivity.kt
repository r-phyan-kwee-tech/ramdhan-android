package com.marmutech.ramdantimetable.ramadantimetable.ui.splash

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatDelegate
import androidx.viewpager.widget.ViewPager
import com.marmutech.ramdantimetable.ramadantimetable.databinding.ActivitySplashBinding
import com.marmutech.ramdantimetable.ramadantimetable.ui.CoreActivity
import com.marmutech.ramdantimetable.ramadantimetable.ui.common.ViewPagerScroller
import com.marmutech.ramdantimetable.ramadantimetable.ui.schedule.ScheduleListActivity
import com.marmutech.ramdantimetable.ramadantimetable.ui.splash.adapter.SplashScreenPagerAdapter
import com.marmutech.ramdantimetable.ramadantimetable.util.UserPrefUtil
import java.lang.reflect.Field
import javax.inject.Inject


class SplashActivity : CoreActivity(), ViewPager.OnPageChangeListener, View.OnClickListener {

    @Inject
    lateinit var userPref: UserPrefUtil

    var screenSlidePagerAdapter: SplashScreenPagerAdapter? = null

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        imageViewPrelolipop()
        if (userPref.isSplashFinished()) {
            val intent = Intent(this, ScheduleListActivity::class.java)
            startActivity(intent)

            finish()
        }
        binding.helpTvBack.visibility = GONE
        pagerButtonUIUpdate(0)

        screenSlidePagerAdapter = SplashScreenPagerAdapter(supportFragmentManager)


        binding.helpViewpager.adapter = screenSlidePagerAdapter
        binding.helpViewpagerIndicator.setViewPager(binding.helpViewpager)


        binding.helpTvBack.setOnClickListener(this)
        binding.helpTvNext.setOnClickListener(this)
        binding.tvFinish.setOnClickListener(this)

        binding.helpViewpagerIndicator.strokeWidth = 2F
        userPref.getStateId()

        changePagerScroller()
        binding.helpViewpager.addOnPageChangeListener(this)
    }

    fun imageViewPrelolipop() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        }


    }

    override fun onClick(p0: View?) {

        if (p0?.tag as Int == 111) {
            val intent: Intent = Intent(this, ScheduleListActivity::class.java)
            startActivity(intent)
            userPref.setSplashFinished(true)
            finish()
        } else {
            binding.helpViewpager.setCurrentItem(p0.tag as Int, true)

        }

    }

    private fun pagerButtonUIUpdate(position: Int) {
        when (position) {
            0 -> {
                binding.apply {
                    tvFinish.visibility = GONE
                    helpTvNext.visibility = VISIBLE
                    helpTvNext.tag = 1
                    helpTvBack.visibility = GONE
                }
            }
            1 -> {
                binding.apply {
                    helpTvNext.tag = 2
                    helpTvNext.visibility = VISIBLE
                    helpTvBack.visibility = VISIBLE
                    tvFinish.visibility = GONE
                    helpTvBack.tag = 0
                }
            }
            2 -> {
                binding.apply {
                    tvFinish.visibility = VISIBLE
                    helpTvNext.visibility = GONE
                    tvFinish.tag = 111
                    helpTvBack.visibility = VISIBLE
                    helpTvBack.tag = 1
                }
            }
        }
    }

    override fun onPageScrollStateChanged(state: Int) {
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageSelected(position: Int) {
        pagerButtonUIUpdate(position)
    }

    private fun changePagerScroller() {
        try {
            var mScroller: Field? = null
            mScroller = ViewPager::class.java.getDeclaredField("mScroller")
            mScroller.isAccessible = true
            val scroller = ViewPagerScroller(this)
            mScroller!!.set(binding.helpViewpager, scroller)
        } catch (e: Exception) {
            Log.e("TAG", "error of change scroller ", e)
        }

    }
}
