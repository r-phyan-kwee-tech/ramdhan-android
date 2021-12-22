package com.marmutech.ramdantimetable.ramadantimetable.ui

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.marmutech.ramdantimetable.ramadantimetable.R

class NoNetworkDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)

        return MaterialAlertDialogBuilder(requireContext())
            .setTitle(resources.getString(R.string.str_no_net_title))
            .setMessage(resources.getString(R.string.no_connection_mm_uni))
            .show()
    }

    companion object {
        val tag: String = NoNetworkDialog::class.java.simpleName
    }
}
