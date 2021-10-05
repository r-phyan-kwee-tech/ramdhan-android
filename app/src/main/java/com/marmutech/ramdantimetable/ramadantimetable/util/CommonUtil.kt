package com.marmutech.ramdantimetable.ramadantimetable.util

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import com.marmutech.ramdantimetable.ramadantimetable.R


class CommonUtil(app: Application) {
    private var mApplication: Application = app

    fun isNetworkConnected(): Boolean {
        val cm =
            mApplication.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val ni = cm.activeNetworkInfo
        return ni != null
    }

    fun getConnectionDialog(isUnicode: Boolean, context: Context): AlertDialog.Builder {
        val dialog = AlertDialog.Builder(context)
        if (isUnicode) {
            dialog.setMessage(context.resources.getString(R.string.no_connection_mm_uni))
        } else {
            dialog.setMessage(context.resources.getString(R.string.no_connection_mm_zg))
        }

        dialog.setNeutralButton("Mobile-Data") { dialogInterface, i ->
            context.startActivity(Intent(Settings.ACTION_APN_SETTINGS))
            dialogInterface.dismiss()

        }
        dialog.setNegativeButton("WI-FI") { dialogInterface, i ->
            context.startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))
            dialogInterface.dismiss()


        }
        dialog.setCancelable(false)
        dialog.setPositiveButton("Cancel") { dialogInterface, i ->
            dialogInterface.dismiss()
            (context as Activity).finish()
        }

        return dialog

    }
}
