package com.marmutech.ramdantimetable.ramadantimetable.ui.setting

import android.os.Bundle
import android.support.annotation.NonNull


import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.marmutech.ramdantimetable.ramadantimetable.R
import com.marmutech.ramdantimetable.ramadantimetable.ui.splash.CountryStateSelectionFragment
import com.marmutech.ramdantimetable.ramadantimetable.ui.splash.FontSelectionFragment
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_setting.*
import javax.inject.Inject

class SettingActivity : AppCompatActivity(), HasSupportFragmentInjector {
    private val TAG_COUNTRY_SELECT_FRAG = "tag_country"

    companion object {
        val KEY_TARGET_FLAG: String = "key_target_flag"
        val FLAG_CHOOSE_LOCATION = "flag_choose_loaction"
        val FLAG_FONT_SUPPORT = "flag_font_support"
        val FLAG_CREDITS = "flag_credits"
        val FLAG_OPEN_SOURCE = "flag_open_source"
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return dispatchingAndroidInjector
    }

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        setSupportActionBar(tool_bar_setting)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        @NonNull var currentFlag=intent.extras.getString(KEY_TARGET_FLAG)

        var targetFragment:Fragment?=null

        when(currentFlag){
            FLAG_CHOOSE_LOCATION-> targetFragment= CountryStateSelectionFragment()
            FLAG_FONT_SUPPORT->targetFragment= FontSelectionFragment()
            FLAG_CREDITS-> /*add credit fragment*/ return
            FLAG_OPEN_SOURCE -> /*add opensource fragment*/ return
        }

        if (savedInstanceState == null && targetFragment!=null) {
            lunchFragmentsDyanamic(targetFragment)
        }
    }

    private fun lunchFragmentsDyanamic(targetFragment: Fragment) {
        this.supportFragmentManager.beginTransaction()
                .replace(R.id.fl_setting_container, targetFragment)
                .commitAllowingStateLoss()
    }
}
