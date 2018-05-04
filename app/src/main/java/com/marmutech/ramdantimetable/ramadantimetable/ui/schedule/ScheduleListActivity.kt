package com.marmutech.ramdantimetable.ramadantimetable.ui.schedule

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.marmutech.ramdantimetable.ramadantimetable.R
import com.marmutech.ramdantimetable.ramadantimetable.model.TimeTableDay
import com.marmutech.ramdantimetable.ramadantimetable.ui.detail.DetailActivity
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_schedule_list_activity.*
import javax.inject.Inject

class ScheduleListActivity : AppCompatActivity(), HasSupportFragmentInjector {
    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return dispatchingAndroidInjector
    }

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule_list_activity)
        setSupportActionBar(toolbar)

        if (savedInstanceState == null) {
            this.supportFragmentManager.beginTransaction()
                    .replace(R.id.container, ScheduleListActivityFragment())
                    .commitAllowingStateLoss()
        }

    }

    fun goToDetail(timeTableDay: TimeTableDay) {
        val intent: Intent = Intent(this, DetailActivity::class.java)
        intent.data = Uri.parse("ramdan://timetable/detail?" + "dayId=" + timeTableDay.objectId)
        startActivity(intent)
    }

}
