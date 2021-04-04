package com.marmutech.ramdantimetable.ramadantimetable.ui.setting


import android.os.Bundle
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import com.marmutech.ramdantimetable.ramadantimetable.R
import com.marmutech.ramdantimetable.ramadantimetable.ui.splash.CountryStateSelectionFragment
import com.marmutech.ramdantimetable.ramadantimetable.ui.splash.FontSelectionFragment
import com.marmutech.ramdantimetable.ramadantimetable.ui.splash.LicenseFragment
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import kotlinx.android.synthetic.main.activity_setting.*
import javax.inject.Inject

class SettingActivity : AppCompatActivity(), HasAndroidInjector {
    private val TAG_COUNTRY_SELECT_FRAG = "tag_country"

    companion object {
        val KEY_TARGET_FLAG: String = "key_target_flag"
        val FLAG_CHOOSE_LOCATION = "Change Location"
        val FLAG_FONT_SUPPORT = "Font Settings"
        val FLAG_CREDITS = "Credits"
        val FLAG_OPEN_SOURCE = "Licenses"
    }

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<androidx.fragment.app.Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        setSupportActionBar(tool_bar_setting)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        @NonNull var currentFlag = intent.extras.getString(KEY_TARGET_FLAG)

        var targetFragment: androidx.fragment.app.Fragment? = null

        when (currentFlag) {
            FLAG_CHOOSE_LOCATION -> targetFragment = CountryStateSelectionFragment()
            FLAG_FONT_SUPPORT -> targetFragment = FontSelectionFragment()
            FLAG_CREDITS -> targetFragment = CreditFragment()
            FLAG_OPEN_SOURCE -> targetFragment = LicenseFragment()
        }
        supportActionBar?.title = intent.extras.getString(KEY_TARGET_FLAG)

        if (savedInstanceState == null && targetFragment != null) {
            lunchFragmentsDyanamic(targetFragment)
        }
    }

    private fun lunchFragmentsDyanamic(targetFragment: androidx.fragment.app.Fragment) {
        this.supportFragmentManager.beginTransaction()
                .replace(R.id.fl_setting_container, targetFragment)
                .commitAllowingStateLoss()
    }

    override fun androidInjector(): AndroidInjector<Any> = androidInjector

}
