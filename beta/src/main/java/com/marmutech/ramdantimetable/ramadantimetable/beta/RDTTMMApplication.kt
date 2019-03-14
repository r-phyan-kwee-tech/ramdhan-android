package com.marmutech.ramdantimetable.ramadantimetable.beta

import android.app.Application
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric

/**
 * a touch of AungMyoLwin on 3/14/19.
 *     made with <3
 */

class RDTTMMApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Fabric.with(this, Crashlytics())
    }
}