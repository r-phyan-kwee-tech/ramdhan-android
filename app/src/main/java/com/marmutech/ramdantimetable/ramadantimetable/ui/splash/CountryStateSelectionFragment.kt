package com.marmutech.ramdantimetable.ramadantimetable.ui.splash


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.AppCompatSpinner
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.marmutech.ramdantimetable.ramadantimetable.R
import com.marmutech.ramdantimetable.ramadantimetable.databinding.FragmentCountrySelectionBinding
import com.marmutech.ramdantimetable.ramadantimetable.di.Injectable
import com.marmutech.ramdantimetable.ramadantimetable.model.Country
import com.marmutech.ramdantimetable.ramadantimetable.model.State
import com.marmutech.ramdantimetable.ramadantimetable.util.UserPrefUtil
import com.marmutech.ramdantimetable.ramadantimetable.vo.Resource
import timber.log.Timber
import javax.inject.Inject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class CountryStateSelectionFragment : Fragment(), Injectable, AdapterView.OnItemSelectedListener {


    var countrySpinner: AppCompatSpinner? = null
    var stateSpinner: AppCompatSpinner? = null
    var countryList: List<Country> = emptyList()
    var stateList: List<State> = emptyList()
    @Inject
    lateinit var prefUtil: UserPrefUtil

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var splashViewModel: SplashViewModel

    private var binding: FragmentCountrySelectionBinding? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        //TODO ProperDataBinding
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_country_selection, container, false)

        splashViewModel = ViewModelProviders.of(this, viewModelFactory).get(SplashViewModel::class.java)



        countrySpinner = binding?.countrySpinner
        stateSpinner = binding?.stateSpinner

        countrySpinner?.onItemSelectedListener = this
        stateSpinner?.onItemSelectedListener = this

        return binding?.root
    }

    override fun onStart() {
        super.onStart()
        var splashViewModel = ViewModelProviders.of(this, viewModelFactory).get(SplashViewModel::class.java)

        splashViewModel.loadAvaliableCountries(50, 1)
        splashViewModel.countryList.observe(this, Observer<Resource<List<Country>>> { t ->
            Timber.d("dayList obersve " + t?.data)
            if (t?.data != null && !t?.data.isEmpty()) {
                countryList = t?.data
                val adapter = ArrayAdapter(this.context, R.layout.row_spinner_item, t?.data)
                adapter.setDropDownViewResource(R.layout.row_spinner_selected_item)
                countrySpinner?.adapter = adapter
            }

        })
    }

    fun updateStateSpinner(countryId: String) {
        splashViewModel.loadAvailableStates(countryId, 50, 1)
        splashViewModel.stateList.observe(this, Observer<Resource<List<State>>> { t ->
            Timber.d("dayList obersve " + t?.data)
            if (t?.data != null && !t?.data.isEmpty()) {
                var stateNameList: List<String> = t.data.map { t ->
                    if (prefUtil.getFont()) {
                        t.nameMmUni
                    } else t.nameMmZawgyi
                }
                stateList = t?.data
                val adapter = ArrayAdapter(this.context, R.layout.row_spinner_item, stateNameList)
                adapter.setDropDownViewResource(R.layout.row_spinner_selected_item)
                stateSpinner?.adapter = adapter
            }

        })
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        when (p0?.id) {
            R.id.countrySpinner -> {
                prefUtil.saveCountryId(countryList.get(p2).objectId)
                updateStateSpinner(countryList.get(p2).objectId)
            }
            R.id.stateSpinner -> {
                prefUtil.saveStateId(stateList.get(p2).objectId)
            }
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}
