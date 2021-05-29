package com.marmutech.ramdantimetable.ramadantimetable.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.marmutech.ramdantimetable.ramadantimetable.di.MainCoroutineDispatcher
import com.marmutech.ramdantimetable.ramadantimetable.domain.country.GetCountryListUseCase
import com.marmutech.ramdantimetable.ramadantimetable.domain.country.GetSelectedCountryIdUseCase
import com.marmutech.ramdantimetable.ramadantimetable.domain.country.SaveSelectedCountryIdUseCase
import com.marmutech.ramdantimetable.ramadantimetable.domain.country.SaveSelectedCountryNameUseCase
import com.marmutech.ramdantimetable.ramadantimetable.domain.fonts.GetIsEnableUnicodeUseCase
import com.marmutech.ramdantimetable.ramadantimetable.domain.fonts.SetIsEnableUnicodeUseCase
import com.marmutech.ramdantimetable.ramadantimetable.domain.state.GetStateListBySelectedCountryUseCase
import com.marmutech.ramdantimetable.ramadantimetable.domain.state.SaveSelectedIdStateUseCase
import com.marmutech.ramdantimetable.ramadantimetable.domain.state.SaveSelectedStateNameUseCase
import com.marmutech.ramdantimetable.ramadantimetable.model.Country
import com.marmutech.ramdantimetable.ramadantimetable.model.State
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class SplashViewModel @Inject constructor(
    @MainCoroutineDispatcher private val dispatcher: CoroutineDispatcher,
    private val setIsEnableUnicodeUseCase: SetIsEnableUnicodeUseCase,
    private val getIsEnableUnicodeUseCase: GetIsEnableUnicodeUseCase,
    private val saveSelectedCountryIdUseCase: SaveSelectedCountryIdUseCase,
    private val getSelectedCountryIdUseCase: GetSelectedCountryIdUseCase,
    private val saveSelectedIdStateUseCase: SaveSelectedIdStateUseCase,
    private val saveSelectedStateNameUseCase: SaveSelectedStateNameUseCase,
    private val saveSelectedCountryNameUseCase: SaveSelectedCountryNameUseCase,
    private val getCountryListUseCase: GetCountryListUseCase,
    private val getStateListBySelectedCountryUseCase: GetStateListBySelectedCountryUseCase
) : ViewModel() {

    private val _countriesSelectionUiModel = MutableStateFlow<CountrySelectionUiModel?>(null)
    val countrySelectionUiModel: StateFlow<CountrySelectionUiModel?> get() = _countriesSelectionUiModel

    private val _fontSelectionUiModel = MutableStateFlow<FontSelectionUiModel?>(null)
    val fontSelectionUiModel: LiveData<FontSelectionUiModel?> get() = _fontSelectionUiModel.asLiveData()

    private val _stateList: MutableStateFlow<List<State>?> = MutableStateFlow(null)
    private val _countryList: MutableStateFlow<List<Country>?> = MutableStateFlow(null)

    fun onViewCreated() {
        viewModelScope.launch(dispatcher) {

            _fontSelectionUiModel.value = FontSelectionUiModel(
                isUnicodeEnable = getIsEnableUnicodeUseCase.execute(
                    Unit
                )
            )

            initCountrySelectionUiModel()
        }
    }

    fun setEnableUnicode(enable: Boolean) {
        viewModelScope.launch(dispatcher) {
            setIsEnableUnicodeUseCase.execute(enable)
        }
    }

    fun setSelectedCountryId(id: String) {
        viewModelScope.launch(dispatcher) {
            //_selectedCountryId.value = id
            saveSelectedCountryIdUseCase.execute(id)
        }
    }

    private suspend fun initCountrySelectionUiModel() {
        combine(
            getCountryListUseCase.execute(Unit),
            flowOf(getSelectedCountryIdUseCase.execute(Unit))
        ) { countries, savedCountryId ->
            val selectedCountriesId = savedCountryId ?: getVeryFirstCountryId(countries)!!
            countries to selectedCountriesId
        }.mapLatest {
            val state = getStateListBySelectedCountryUseCase.execute(it.second).single()
            it.first to state
        }.collect {
            saveCountryListInVM(it.first)
            saveStateListInVM(it.second)
            _countriesSelectionUiModel.value = CountrySelectionUiModel(
                getNameFromCountry(it.first),
                getNameFromState(it.second)
            )
        }
    }

    fun onCountrySelected(position: Int) {
        viewModelScope.launch {
            _countryList.value?.let {
                val countryIdAndStateName = getCountryIdAndNameFromCountryByPosition(it, position)
                    ?: return@launch
                saveSelectedCountryIdUseCase.execute(countryIdAndStateName.first)
                saveSelectedCountryNameUseCase.execute(countryIdAndStateName.second)
                Timber.d("country data saved")
            }
        }
    }

    fun onStateSelected(position: Int) {
        viewModelScope.launch {
            _stateList.value?.let {
                val stateIdAndStateName = getStateIdAndNameFromStateByPosition(it, position)
                    ?: return@launch
                saveSelectedIdStateUseCase.execute(stateIdAndStateName.first)
                saveSelectedStateNameUseCase.execute(stateIdAndStateName.second)
                Timber.d("state data saved")
            }
        }
    }

    private fun getVeryFirstCountryId(countries: List<Country>): String? = countries.getOrNull(0)?.objectId

    private fun getNameFromCountry(countries: List<Country>): List<String> = countries.map { it.name }

    //todo fix to one unifine name
    private fun getNameFromState(state: List<State>): List<String> = state.map { it.nameMmUni }

    private fun getStateIdAndNameFromStateByPosition(
        state: List<State>,
        position: Int
    ) = state.getOrNull(position)?.run {
        this.objectId to this.nameMmUni
    }

    private fun getCountryIdAndNameFromCountryByPosition(
        country: List<Country>,
        position: Int
    ) = country.getOrNull(position)?.run {
        this.objectId to this.name
    }

    private fun saveCountryListInVM(country: List<Country>) {
        _countryList.value = country
    }

    private fun saveStateListInVM(state: List<State>) {
        _stateList.value = state
    }
}

data class CountrySelectionUiModel(
    val countries: List<String>,
    val states: List<String>
)

data class FontSelectionUiModel(
    val isUnicodeEnable: Boolean
)
