package com.marmutech.ramdantimetable.ramadantimetable.ui.schedule

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import com.marmutech.ramdantimetable.ramadantimetable.R

import kotlinx.android.synthetic.main.activity_schedule_list_activity.*

class ScheduleListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule_list_activity)
        setSupportActionBar(toolbar)


    }

}
