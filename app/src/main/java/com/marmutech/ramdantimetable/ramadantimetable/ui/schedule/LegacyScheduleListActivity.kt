package com.marmutech.ramdantimetable.ramadantimetable.ui.schedule

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.marmutech.ramdantimetable.ramadantimetable.R
import com.marmutech.ramdantimetable.ramadantimetable.model.TimeTableDay
import com.marmutech.ramdantimetable.ramadantimetable.ui.CoreActivity
import com.marmutech.ramdantimetable.ramadantimetable.ui.setting.SettingActivity
import com.marmutech.ramdantimetable.ramadantimetable.util.UserPrefUtil
import kotlinx.android.synthetic.main.activity_schedule_list_activity.*
import org.rabbitconverter.rabbit.Rabbit
import javax.inject.Inject


class LegacyScheduleListActivity : CoreActivity() {
    @Inject
    lateinit var prefUtil: UserPrefUtil

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule_list_activity)
        setSupportActionBar(toolbar)

        if (prefUtil.getFont()) {
            supportActionBar?.title = String.format(
                resources.getString(R.string.str_schedule_uni),
                prefUtil.getStateName()
            )
        } else {
            supportActionBar?.title = String.format(
                resources.getString(R.string.str_schedule_zg),
                Rabbit.uni2zg(prefUtil.getStateName())
            )
        }

//        if (savedInstanceState == null) {
//            this.supportFragmentManager.beginTransaction()
//                    .replace(R.id.fl_schedule_container, LegacyScheduleListFragment())
//                    .commitAllowingStateLoss()
//        }

    }

    override fun onResume() {
        super.onResume()
        if (prefUtil.getFont()) {
            supportActionBar?.title = String.format(
                resources.getString(R.string.str_schedule_uni),
                prefUtil.getStateName()
            )
        } else {
            supportActionBar?.title = String.format(
                resources.getString(R.string.str_schedule_zg),
                Rabbit.uni2zg(prefUtil.getStateName())
            )
        }
//        this.supportFragmentManager.beginTransaction()
//                .replace(R.id.fl_schedule_container, LegacyScheduleListFragment())
//                .commitAllowingStateLoss()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu_schedule_list, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        super.onOptionsItemSelected(item)
        when (item?.itemId) {
            R.id.action_info -> lunchInfoBottomSheet()
        }

        return true
    }

    fun goToDetail(timeTableDay: TimeTableDay) {
        val intent: Intent = Intent(this, LegacyDetailActivity::class.java)
        intent.data = Uri.parse("ramdan://timetable/detail?" + "dayId=" + timeTableDay.objectId)
        startActivity(intent)
    }

    private fun lunchInfoBottomSheet() {
        val bottomSheetFragment = InfoBottomSheetFragment()
        bottomSheetFragment.show(supportFragmentManager, InfoBottomSheetFragment.TAG)
    }

    fun lunchInfoActivity(targetFlag: String) {
        val intent = Intent(this, SettingActivity::class.java)
        intent.putExtra(SettingActivity.KEY_TARGET_FLAG, targetFlag)
        startActivity(intent)
    }
}
