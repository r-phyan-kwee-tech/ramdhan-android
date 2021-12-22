package com.marmutech.ramdantimetable.ramadantimetable.ui.splash

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.marmutech.ramdantimetable.ramadantimetable.R
import com.marmutech.ramdantimetable.ramadantimetable.di.MainCoroutineDispatcher
import com.marmutech.ramdantimetable.ramadantimetable.domain.SaveIsOnBoardingFinishUseCase
import com.marmutech.ramdantimetable.ramadantimetable.domain.country.GetCountryListUseCase
import com.marmutech.ramdantimetable.ramadantimetable.domain.country.GetSelectedCountryIdUseCase
import com.marmutech.ramdantimetable.ramadantimetable.domain.country.SaveSelectedCountryIdUseCase
import com.marmutech.ramdantimetable.ramadantimetable.domain.country.SaveSelectedCountryNameUseCase
import com.marmutech.ramdantimetable.ramadantimetable.domain.state.GetSelectedStateIdUseCase
import com.marmutech.ramdantimetable.ramadantimetable.domain.state.GetStateListBySelectedCountryUseCase
import com.marmutech.ramdantimetable.ramadantimetable.domain.state.SaveSelectedStateIdUseCase
import com.marmutech.ramdantimetable.ramadantimetable.domain.state.SaveSelectedStateNameUseCase
import com.marmutech.ramdantimetable.ramadantimetable.model.Country
import com.marmutech.ramdantimetable.ramadantimetable.model.State
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class SplashViewModel @Inject constructor(
    @MainCoroutineDispatcher private val dispatcher: CoroutineDispatcher,
    private val saveSelectedCountryIdUseCase: SaveSelectedCountryIdUseCase,
    private val getSelectedCountryIdUseCase: GetSelectedCountryIdUseCase,
    private val saveSelectedStateIdUseCase: SaveSelectedStateIdUseCase,
    private val getSelectedIdStateUseCase: GetSelectedStateIdUseCase,
    private val saveSelectedStateNameUseCase: SaveSelectedStateNameUseCase,
    private val saveSelectedCountryNameUseCase: SaveSelectedCountryNameUseCase,
    private val getCountryListUseCase: GetCountryListUseCase,
    private val getStateListBySelectedCountryUseCase: GetStateListBySelectedCountryUseCase,
    private val saveIsOnBoardingFinishUseCase: SaveIsOnBoardingFinishUseCase
) : ViewModel() {

    private val _countriesSelectionUiModel = MutableStateFlow<CountrySelectionUiModel?>(null)
    val countrySelectionUiModel: StateFlow<CountrySelectionUiModel?> get() = _countriesSelectionUiModel

    private val _movePageByPosition = MutableStateFlow(0)
    val movePageByPosition: LiveData<Int> get() = _movePageByPosition.asLiveData()

    private val _onBoardUiModel = MutableStateFlow<OnBoardUiModel?>(null)
    val onBoardUiModel: LiveData<OnBoardUiModel?> get() = _onBoardUiModel.asLiveData()

    private val _openScheduleList = MutableStateFlow(false)
    val openScheduleList: LiveData<Boolean> get() = _openScheduleList.asLiveData()

    private val _stateList: MutableStateFlow<List<State>?> = MutableStateFlow(null)
    private val _countryList: MutableStateFlow<List<Country>?> = MutableStateFlow(null)
    private var totalPageCount = 0
    private var currentPagePosition = 0

    @ExperimentalCoroutinesApi
    fun loadData() {
        viewModelScope.launch(dispatcher) {
            initCountrySelectionUiModel()
        }
    }

    @ExperimentalCoroutinesApi
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
            handleCountryAndStateData(it.first, it.second)
        }
    }

    fun onCountrySelected(position: Int) {
        viewModelScope.launch {
            _countryList.value?.let {
                val countryIdAndStateName = getCountryIdAndNameFromCountryByPosition(it, position)
                    ?: return@launch
                saveSelectedCountryIdUseCase.execute(countryIdAndStateName.first)
                saveSelectedCountryNameUseCase.execute(countryIdAndStateName.second)
                Timber.d("banner: country data saved")
            }
        }
    }

    fun onStateSelected(position: Int) {
        viewModelScope.launch {
            _stateList.value?.let {
                val stateIdAndStateName = getStateIdAndNameFromStateByPosition(it, position)
                    ?: return@launch
                saveSelectedStateIdUseCase.execute(stateIdAndStateName.first)
                saveSelectedStateNameUseCase.execute(stateIdAndStateName.second)
                Timber.d("banner: state data saved")
            }
        }
    }

    fun onNextClick() {
        viewModelScope.launch(dispatcher) { decideNextOrEnd() }
    }

    fun onPrevClick() {
        currentPagePosition--
        _movePageByPosition.value = currentPagePosition
        if (currentPagePosition <= 0) {
            //hide prev button
            _onBoardUiModel.value = onBoardUiModel.value?.copy(showPrevButton = false)
        } else {
            //show prev button
            _onBoardUiModel.value = onBoardUiModel.value?.copy(showPrevButton = true)
        }
    }

    fun setTotalPageCount(count: Int) {
        totalPageCount = count
    }

    private suspend fun decideNextOrEnd() {
        if (currentPagePosition == totalPageCount - 1) {
            saveIsOnBoardingFinishUseCase.execute(true)
            //go to list page
            _openScheduleList.value = true
        } else {
            //go to next page
            currentPagePosition++
            _movePageByPosition.value = currentPagePosition
        }
    }

    private fun handleCountryAndStateData(countries: List<Country>, states: List<State>) {
        viewModelScope.launch {
            saveCountryListInVM(countries)
            saveStateListInVM(states)
            _countriesSelectionUiModel.value = CountrySelectionUiModel(
                CountryListUiModel(
                    getNameFromCountry(countries),
                    getCountrySelectedIndex(countries, getSelectedCountryIdUseCase.execute(Unit))
                ),
                StateListUiModel(
                    getNameFromState(states),
                    getStateSelectedIndex(states, getSelectedIdStateUseCase.execute(Unit))
                ),
                mapSelectionText()
            )
        }
    }

    private fun getVeryFirstCountryId(countries: List<Country>): String? =
        countries.getOrNull(0)?.objectId

    private fun getNameFromCountry(countries: List<Country>): List<String> =
        countries.map { it.name }

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

    private fun getCountrySelectedIndex(countries: List<Country>, savedId: String?): Int {
        if (savedId == null) return 0

        return countries.indexOfFirst {
            it.objectId == savedId
        }
    }

    private fun getStateSelectedIndex(state: List<State>, savedId: String?): Int {
        if (savedId == null) return 0

        return state.indexOfFirst {
            it.objectId == savedId
        }
    }

    private fun mapSelectionText(): SelectionText {
        return SelectionText(
            R.string.uni_country_select,
            R.string.uni_country_mm,
            R.string.uni_state_mm
        )
    }
}

data class OnBoardUiModel(
    val showPrevButton: Boolean
)

data class CountrySelectionUiModel(
    val countryListUiModel: CountryListUiModel,
    val stateListUiModel: StateListUiModel,
    val selectionText: SelectionText
)

data class CountryListUiModel(
    val countries: List<String>,
    val selectedIndex: Int
)

data class StateListUiModel(
    val states: List<String>,
    val selectedIndex: Int
)

data class SelectionText(
    @StringRes val selectionTitleText: Int,
    @StringRes val selectionCountryText: Int,
    @StringRes val selectionStateText: Int
)
