package com.marmutech.ramdantimetable.ramadantimetable.ui.detail.duapager


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.marmutech.ramdantimetable.ramadantimetable.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class DuaInfoFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dua_info, container, false)
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param issueId Parameter 1.
         * @param issueNumber Parameter 2.
         * @return A new instance of fragment BlankFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(issueId: String, issueNumber: String) =
                DuaInfoFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, issueId)

                        putString(ARG_PARAM2, issueNumber)
                    }
                }
    }


}
