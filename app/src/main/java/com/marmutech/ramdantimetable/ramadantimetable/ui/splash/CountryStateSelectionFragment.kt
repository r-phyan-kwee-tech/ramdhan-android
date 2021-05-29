package com.marmutech.ramdantimetable.ramadantimetable.ui.splash


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.widget.AppCompatSpinner
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.marmutech.ramdantimetable.ramadantimetable.R
import com.marmutech.ramdantimetable.ramadantimetable.databinding.FragmentCountrySelectionBinding
import com.marmutech.ramdantimetable.ramadantimetable.model.Country
import com.marmutech.ramdantimetable.ramadantimetable.model.State
import com.marmutech.ramdantimetable.ramadantimetable.ui.CoreFragment
import com.marmutech.ramdantimetable.ramadantimetable.util.UserPrefUtil
import timber.log.Timber
import javax.inject.Inject

class CountryStateSelectionFragment : CoreFragment() {

    var countrySpinner: AppCompatSpinner? = null
    var stateSpinner: AppCompatSpinner? = null
    var countryList: List<Country> = emptyList()
    var localStateList: List<State> = emptyList()

    @Inject
    lateinit var prefUtil: UserPrefUtil

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var legacySplashViewModel: LegacySplashViewModel
    private val splashViewModel by lazy {
        ViewModelProvider(requireActivity(), viewModelFactory).get(SplashViewModel::class.java)
    }

    private var binding: FragmentCountrySelectionBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //TODO ProperDataBinding
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_country_selection,
            container,
            false
        )

        legacySplashViewModel = ViewModelProvider(
            this,
            viewModelFactory
        ).get(LegacySplashViewModel::class.java)



        countrySpinner = binding?.countrySpinner
        stateSpinner = binding?.stateSpinner

        countrySpinner?.setBackgroundDrawable(this.resources.getDrawable(R.drawable.bg_dropdown))
        stateSpinner?.setBackgroundDrawable(this.resources.getDrawable(R.drawable.bg_dropdown))

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        splashViewModel.onViewCreated()
    }

    override fun onStart() {
        super.onStart()
        binding?.isUnicode = prefUtil.getFont()
    }

    fun updateStateSpinner(countryId: String) {
        //legacySplashViewModel.loadAvailableStates(countryId, 900, 1)
    }

//    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
////        when (p0?.id) {
////            R.id.countrySpinner -> {
////                splashViewModel.setSelectedCountryId(countryList[p2].objectId)
////                updateStateSpinner(countryList.get(p2).objectId)
////            }
////            R.id.stateSpinner -> {
////                prefUtil.saveStateId(localStateList.get(p2).objectId)
////                prefUtil.saveStateName(localStateList.get(p2).nameMmUni)
////            }
////        }
//    }
//
//    override fun onNothingSelected(p0: AdapterView<*>?) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }

    private fun observeData() {
        splashViewModel.countrySelectionUiModel.asLiveData()
            .observe(viewLifecycleOwner, { uiModel ->
                uiModel?.let {
                    Timber.d("banner: county index: ${it.countryListUiModel.selectedIndex} and state index: ${it.stateListUiModel.selectedIndex}")
                    showCountryDataInSpinner(it.countryListUiModel)
                    showStateDataInSpinner(it.stateListUiModel)
                    attachSpinnerListener()
                }
            })
    }

    private fun showCountryDataInSpinner(uiModel: CountryListUiModel) {
        val adapter = ArrayAdapter(requireContext(), R.layout.row_spinner_item, uiModel.countries)
        adapter.setDropDownViewResource(R.layout.row_spinner_selected_item)
        countrySpinner?.adapter = adapter
        countrySpinner?.setSelection(uiModel.selectedIndex)
    }

    private fun showStateDataInSpinner(uiModel: StateListUiModel) {
        val adapter = ArrayAdapter(requireContext(), R.layout.row_spinner_item, uiModel.states)
        adapter.setDropDownViewResource(R.layout.row_spinner_selected_item)
        stateSpinner?.adapter = adapter
        stateSpinner?.setSelection(uiModel.selectedIndex)
    }

    private fun attachSpinnerListener() {
        countrySpinner?.onItemSelectedListener = countrySelectionListener
        stateSpinner?.onItemSelectedListener = stateSelectionListener
    }

    private val countrySelectionListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            splashViewModel.onCountrySelected(position)
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {
            TODO("Not yet implemented")
        }

    }

    private val stateSelectionListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            splashViewModel.onStateSelected(position)
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {
            TODO("Not yet implemented")
        }

    }
}
