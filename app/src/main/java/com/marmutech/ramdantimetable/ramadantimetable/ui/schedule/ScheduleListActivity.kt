package com.marmutech.ramdantimetable.ramadantimetable.ui.schedule

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.marmutech.ramdantimetable.ramadantimetable.R
import com.marmutech.ramdantimetable.ramadantimetable.R.id.toolbar
import com.marmutech.ramdantimetable.ramadantimetable.model.TimeTableDay
import kotlinx.android.synthetic.main.activity_schedule_list_activity.*

class ScheduleListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule_list_activity)
        setSupportActionBar(toolbar)


    }

    fun goToDetail(timeTableDay: TimeTableDay) {
        val intent: Intent = Intent(this, DetailActivity::class.java)
        intent.data = Uri.parse("ramdan://timetable/detail?" + "dayId=" + timeTableDay.objectId)
        startActivity(intent)
    }

}
