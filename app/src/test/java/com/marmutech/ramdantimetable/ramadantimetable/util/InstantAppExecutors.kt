package com.marmutech.ramdantimetable.ramadantimetable.util


import com.marmutech.ramdantimetable.ramadantimetable.AppExecutors
import java.util.concurrent.Executor

class InstantAppExecutors : AppExecutors(instant, instant, instant) {
    companion object {
        private val instant = Executor { it.run() }
    }
}