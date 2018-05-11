package com.marmutech.ramdantimetable.ramadantimetable.ui.splash

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import com.marmutech.ramdantimetable.ramadantimetable.model.Country
import com.marmutech.ramdantimetable.ramadantimetable.model.State
import com.marmutech.ramdantimetable.ramadantimetable.repository.CountryRepository
import com.marmutech.ramdantimetable.ramadantimetable.repository.StateRepository
import com.marmutech.ramdantimetable.ramadantimetable.util.AbsentLiveData
import com.marmutech.ramdantimetable.ramadantimetable.vo.Resource
import javax.inject.Inject


class SplashViewModel @Inject constructor(countryRepo: CountryRepository, stateRepo: StateRepository) : ViewModel() {


    private val _countryParam = MutableLiveData<CountryListParam>()
    private val _stateParam = MutableLiveData<StateListParam>()


    var countryList: LiveData<Resource<List<Country>>> = Transformations.switchMap(_countryParam) { input ->
        input.ifExists { limit, page ->
            countryRepo.loadCountryList(limit, page)
        }
    }

    var stateList: LiveData<Resource<List<State>>> = Transformations.switchMap(_stateParam) { input ->
        input.ifExists { countryId, limit, page ->
            stateRepo.loadStateList(countryId, limit, page)
        }
    }


    fun loadAvailableStates(countryId: String, limit: Int, page: Int) {
        val update = StateListParam(countryId, limit, page)
        if (_stateParam.value == update) {
            return
        }
        _stateParam.value = update
    }

    /**
     * Load Available Countries
     */
    fun loadAvaliableCountries(limit: Int, page: Int) {
        val update = CountryListParam(limit, page)
        if (_countryParam.value == update) {
            return
        }
        _countryParam.value = update
    }


    data class StateListParam(val countryId: String?, val limit: Int?, val page: Int?) {
        fun <T> ifExists(f: (String, Int, Int) -> LiveData<T>): LiveData<T> {
            return if (countryId.isNullOrBlank() || limit == null || page == null) {
                AbsentLiveData.create()
            } else {
                f(countryId!!, limit, page)
            }
        }
    }

    data class CountryListParam(val limit: Int?, val page: Int?) {
        fun <T> ifExists(f: (Int, Int) -> LiveData<T>): LiveData<T> {
            return if (limit == null || page == null) {
                AbsentLiveData.create()
            } else {
                f(limit, page)
            }
        }
    }


}