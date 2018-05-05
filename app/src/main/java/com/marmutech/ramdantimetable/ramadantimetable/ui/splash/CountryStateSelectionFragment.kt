package com.marmutech.ramdantimetable.ramadantimetable.ui.splash


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.AppCompatSpinner
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.marmutech.ramdantimetable.ramadantimetable.R
import com.marmutech.ramdantimetable.ramadantimetable.di.Injectable
import com.marmutech.ramdantimetable.ramadantimetable.model.Country
import com.marmutech.ramdantimetable.ramadantimetable.model.State

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class CountryStateSelectionFragment : Fragment(), Injectable {
    var countrySpinner: AppCompatSpinner? = null
    var stateSpinner: AppCompatSpinner? = null
    var countryList: List<Country> = emptyList()
    var stateList: List<State> = emptyList()
    var demoList: List<String> = listOf("a", "b", "c", "d", "e", "f")


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var v = inflater.inflate(R.layout.fragment_country_selection, container, false)
        val adapter = ArrayAdapter(this.context, R.layout.row_spinner_item, demoList)
        adapter.setDropDownViewResource(R.layout.row_spinner_selected_item)
        countrySpinner = v.findViewById(R.id.countrySpinner)

        stateSpinner = v.findViewById(R.id.stateSpinner)
        countrySpinner?.adapter = adapter
        stateSpinner?.adapter = adapter
        return v
    }


}
