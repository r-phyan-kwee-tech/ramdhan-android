package com.marmutech.ramdantimetable.ramadantimetable.ui.splash


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.marmutech.ramdantimetable.ramadantimetable.R
import com.marmutech.ramdantimetable.ramadantimetable.databinding.FragmentCountrySelectionBinding
import com.marmutech.ramdantimetable.ramadantimetable.ui.CoreFragment
import timber.log.Timber
import javax.inject.Inject

class CountryStateSelectionFragment : CoreFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val splashViewModel by lazy {
        ViewModelProvider(requireActivity(), viewModelFactory).get(SplashViewModel::class.java)
    }

    private var _binding: FragmentCountrySelectionBinding? = null
    private val binding: FragmentCountrySelectionBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCountrySelectionBinding.inflate(layoutInflater, container, false)

        //binding.countrySpinner.setBackgroundResource(R.drawable.bg_dropdown)
        //binding.stateSpinner.setBackgroundResource(R.drawable.bg_dropdown)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        splashViewModel.onViewCreated()
    }

    private fun observeData() {
        splashViewModel.countrySelectionUiModel.asLiveData()
            .observe(viewLifecycleOwner, { uiModel ->
                uiModel?.let {
                    Timber.d("banner: county index: ${it.countryListUiModel.selectedIndex} and state index: ${it.stateListUiModel.selectedIndex}")
                    showCountryDataInSpinner(it.countryListUiModel)
                    showStateDataInSpinner(it.stateListUiModel)
                    setTitleText(it.selectionText)
                    attachSpinnerListener()
                }
            })
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun setTitleText(stringRes: SelectionText) {
        binding.selectionTitleTextView.setText(stringRes.selectionTitleText)
        binding.countryTextInputLayout.setHint(stringRes.selectionCountryText)
        binding.stateTextInputLayout.setHint(stringRes.selectionStateText)
    }

    private fun showCountryDataInSpinner(uiModel: CountryListUiModel) {
        val adapter = ArrayAdapter(requireContext(), R.layout.row_spinner_item, uiModel.countries)
        //adapter.setDropDownViewResource(R.layout.row_spinner_selected_item)
        (binding.countryTextInputLayout.editText as? AutoCompleteTextView)?.setAdapter(adapter)
        //binding.countrySpinner.setSelection(uiModel.selectedIndex)
    }

    private fun showStateDataInSpinner(uiModel: StateListUiModel) {
        val adapter = ArrayAdapter(requireContext(), R.layout.row_spinner_item, uiModel.states)
        //adapter.setDropDownViewResource(R.layout.row_spinner_selected_item)
        (binding.stateTextInputLayout.editText as? AutoCompleteTextView)?.setAdapter(adapter)
        //binding.stateSpinner.setSelection(uiModel.selectedIndex)
    }

    private fun attachSpinnerListener() {
        (binding.countryTextInputLayout.editText as? AutoCompleteTextView)?.onItemSelectedListener =
            countrySelectionListener
        (binding.stateTextInputLayout.editText as? AutoCompleteTextView)?.onItemSelectedListener =
            stateSelectionListener
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
