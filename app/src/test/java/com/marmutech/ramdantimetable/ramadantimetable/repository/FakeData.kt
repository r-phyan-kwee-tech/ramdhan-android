package com.marmutech.ramdantimetable.ramadantimetable.repository

import com.marmutech.ramdantimetable.ramadantimetable.model.Countries
import com.marmutech.ramdantimetable.ramadantimetable.model.Country
import com.marmutech.ramdantimetable.ramadantimetable.model.CountryResponse
import com.marmutech.ramdantimetable.ramadantimetable.model.Data

object FakeData {
    fun getCountryList(): List<Country> {
        return listOf(getCountry())
    }

    fun getCountry(): Country {
        return Country("id", "objectId", "name", 123, 123)
    }

    fun getCountryResponse() = CountryResponse(Data(countries = Countries(data = getCountryList())))
}
