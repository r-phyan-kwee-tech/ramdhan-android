package com.marmutech.ramdantimetable.ramadantimetable.ui.common

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("visibleGone")
fun showHide(view: View, show: Boolean) {
    view.visibility = if (show) View.VISIBLE else View.GONE
}
