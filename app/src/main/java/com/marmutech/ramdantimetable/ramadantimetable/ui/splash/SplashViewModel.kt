package com.marmutech.ramdantimetable.ramadantimetable.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.marmutech.ramdantimetable.ramadantimetable.di.MainCoroutineDispatcher
import com.marmutech.ramdantimetable.ramadantimetable.domain.country.GetCountryListUseCase
import com.marmutech.ramdantimetable.ramadantimetable.domain.country.GetSelectedCountryIdUseCase
import com.marmutech.ramdantimetable.ramadantimetable.domain.country.SaveSelectedCountryIdUseCase
import com.marmutech.ramdantimetable.ramadantimetable.domain.fonts.GetIsEnableUnicodeUseCase
import com.marmutech.ramdantimetable.ramadantimetable.domain.fonts.SetIsEnableUnicodeUseCase
import com.marmutech.ramdantimetable.ramadantimetable.domain.state.GetStateListBySelectedCountryUseCase
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
import javax.inject.Inject

class SplashViewModel @Inject constructor(
    @MainCoroutineDispatcher private val dispatcher: CoroutineDispatcher,
    private val setIsEnableUnicodeUseCase: SetIsEnableUnicodeUseCase,
    private val getIsEnableUnicodeUseCase: GetIsEnableUnicodeUseCase,
    private val saveSelectedCountryIdUseCase: SaveSelectedCountryIdUseCase,
    private val getSelectedCountryIdUseCase: GetSelectedCountryIdUseCase,
    private val getCountryListUseCase: GetCountryListUseCase,
    private val getStateListBySelectedCountryUseCase: GetStateListBySelectedCountryUseCase
) : ViewModel() {

    private val _countriesSelectionUiModel = MutableStateFlow<CountrySelectionUiModel?>(null)
    val countrySelectionUiModel: StateFlow<CountrySelectionUiModel?> get() = _countriesSelectionUiModel

    private val _fontSelectionUiModel = MutableStateFlow<FontSelectionUiModel?>(null)
    val fontSelectionUiModel: LiveData<FontSelectionUiModel?> get() = _fontSelectionUiModel.asLiveData()

    private val _selectedCountryId = MutableStateFlow<String?>(null)

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
            _countriesSelectionUiModel.value = CountrySelectionUiModel(
                getNameFromCountry(it.first),
                getNameFromState(it.second)
            )
        }
    }

    private fun getVeryFirstCountryId(countries: List<Country>): String? = countries.getOrNull(0)?.objectId

    private fun getNameFromCountry(countries: List<Country>): List<String> = countries.map { it.name }

    //todo fix to one unifine name
    private fun getNameFromState(state: List<State>): List<String> = state.map { it.nameMmUni }
}

data class CountrySelectionUiModel(
    val countries: List<String>,
    val states: List<String>
)

data class FontSelectionUiModel(
    val isUnicodeEnable: Boolean
)
