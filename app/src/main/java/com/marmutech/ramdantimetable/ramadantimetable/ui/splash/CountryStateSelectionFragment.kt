package com.marmutech.ramdantimetable.ramadantimetable.ui.splash


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.widget.AppCompatSpinner
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.marmutech.ramdantimetable.ramadantimetable.R
import com.marmutech.ramdantimetable.ramadantimetable.databinding.FragmentCountrySelectionBinding
import com.marmutech.ramdantimetable.ramadantimetable.model.Country
import com.marmutech.ramdantimetable.ramadantimetable.model.State
import com.marmutech.ramdantimetable.ramadantimetable.ui.CoreFragment
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
class CountryStateSelectionFragment : CoreFragment(), AdapterView.OnItemSelectedListener {


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

        splashViewModel = ViewModelProvider(this, viewModelFactory).get(SplashViewModel::class.java)



        countrySpinner = binding?.countrySpinner
        stateSpinner = binding?.stateSpinner

        countrySpinner?.onItemSelectedListener = this
        stateSpinner?.onItemSelectedListener = this

        countrySpinner?.setBackgroundDrawable(this.resources.getDrawable(R.drawable.bg_dropdown))
        stateSpinner?.setBackgroundDrawable(this.resources.getDrawable(R.drawable.bg_dropdown))

        return binding?.root
    }

    override fun onStart() {
        super.onStart()
        var splashViewModel = ViewModelProvider(
            this,
            viewModelFactory
        ).get(SplashViewModel::class.java)
        binding?.isUnicode = prefUtil.getFont()
        splashViewModel.loadAvaliableCountries(50, 1)
        splashViewModel.countryList.observe(this, Observer<Resource<List<Country>>> { t ->
            Timber.d("dayList obersve " + t?.data)
            if (t?.data != null && !t?.data.isEmpty()) {
                countryList = t?.data
                val adapter = ArrayAdapter(this.context, R.layout.row_spinner_item, t?.data)
                adapter.setDropDownViewResource(R.layout.row_spinner_selected_item)
                countrySpinner?.adapter = adapter
                countrySpinner?.setSelection(0)
            }

        })
    }

    fun updateStateSpinner(countryId: String) {
        splashViewModel.loadAvailableStates(countryId, 900, 1)
        splashViewModel.stateList.observe(this, Observer<Resource<List<State>>> { stateData ->
            Timber.d("dayList obersve  ${stateData?.data}")
            if (stateData?.data != null && stateData.data.isNotEmpty()) {
                val stateNameList = stateData.data.map { t ->
                    if (prefUtil.getFont()) {
                        t.nameMmUni
                    } else t.nameMmZawgyi
                }.sorted()
                stateList = stateData.data.sortedBy {
                    if (prefUtil.getFont()) {
                        it.nameMmUni
                    } else {
                        it.nameMmZawgyi
                    }
                }
                val adapter = ArrayAdapter(context, R.layout.row_spinner_item, stateNameList)
                adapter.setDropDownViewResource(R.layout.row_spinner_selected_item)
                stateSpinner?.adapter = adapter
            }

        })
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        when (p0?.id) {
            R.id.countrySpinner -> {
                prefUtil.saveLocationId(countryList.get(p2).objectId)
                updateStateSpinner(countryList.get(p2).objectId)
            }
            R.id.stateSpinner -> {
                prefUtil.saveStateId(stateList.get(p2).objectId)
                prefUtil.saveStateName(stateList.get(p2).nameMmUni)
            }
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}
