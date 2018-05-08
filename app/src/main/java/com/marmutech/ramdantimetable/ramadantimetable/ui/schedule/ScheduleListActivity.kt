package com.marmutech.ramdantimetable.ramadantimetable.ui.schedule

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.marmutech.ramdantimetable.ramadantimetable.R
import com.marmutech.ramdantimetable.ramadantimetable.R.id.toolbar
import com.marmutech.ramdantimetable.ramadantimetable.model.TimeTableDay
import com.marmutech.ramdantimetable.ramadantimetable.ui.detail.DetailActivity
import com.marmutech.ramdantimetable.ramadantimetable.ui.setting.SettingActivity
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
                    .replace(R.id.fl_schedule_container, ScheduleListActivityFragment())
                    .commitAllowingStateLoss()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu_schedule_list_acitivity, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
         super.onOptionsItemSelected(item)
        when(item?.itemId){
            R.id.action_info -> lunchInfoBottomSheet()
        }

        return true
    }

    fun goToDetail(timeTableDay: TimeTableDay) {
        val intent: Intent = Intent(this, DetailActivity::class.java)
        intent.data = Uri.parse("ramdan://timetable/detail?" + "dayId=" + timeTableDay.objectId)
        startActivity(intent)
    }

    /*private fun lunchSettingActivity() {
        //we will replace with start activity for result later
        val intent= Intent(this,SettingActivity::class.java)
        startActivity(intent)
    }*/

    private fun lunchInfoBottomSheet(){
        val bottomSheetFragment= InfoBottomSheetFragment()
        bottomSheetFragment.show(supportFragmentManager,InfoBottomSheetFragment.TAG)
    }

    fun lunchInfoActivity(targetFlag:String){
        val intent=Intent(this,SettingActivity::class.java)
        intent.putExtra(SettingActivity.KEY_TARGET_FLAG,targetFlag)
        startActivity(intent)
    }

}
