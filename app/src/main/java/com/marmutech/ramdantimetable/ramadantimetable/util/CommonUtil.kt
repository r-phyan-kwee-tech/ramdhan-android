package com.marmutech.ramdantimetable.ramadantimetable.util

import android.app.Application
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.ConnectivityManager
import android.provider.Settings
import android.support.v7.app.AlertDialog
import com.marmutech.ramdantimetable.ramadantimetable.R


class CommonUtil(app: Application) {
    private lateinit var mApplication: Application

    init {
        mApplication = app

    }

    fun isNetworkConnected(): Boolean {
        val cm = mApplication.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val ni = cm.activeNetworkInfo
        return if (ni == null) {
            // There are no active networks.
            false
        } else
            true
    }

    fun getConnectionDialog(isUnicode: Boolean, context: Context): AlertDialog.Builder {
        val dialog = AlertDialog.Builder(context, R.style.myDialog)
        if (isUnicode) {
            dialog.setMessage(context.resources.getString(R.string.no_connection_mm_uni))
        } else {
            dialog.setMessage(context.resources.getString(R.string.no_connection_mm_zg))
        }

        dialog.setNeutralButton("Mobile-Data", DialogInterface.OnClickListener { dialogInterface, i ->
            context.startActivity(Intent(Settings.ACTION_APN_SETTINGS))
            dialogInterface.dismiss()

        })
        dialog.setNegativeButton("WI-FI", DialogInterface.OnClickListener { dialogInterface, i ->
            context.startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))
            dialogInterface.dismiss()

        })
        dialog.setCancelable(false)
        dialog.setPositiveButton("Cancel", DialogInterface.OnClickListener { dialogInterface, i -> dialogInterface.dismiss() })

        return dialog

    }
}