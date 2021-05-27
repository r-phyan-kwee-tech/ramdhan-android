package com.marmutech.ramdantimetable.ramadantimetable.repository

import com.marmutech.ramdantimetable.ramadantimetable.model.Country

object FakeTimeTableRepository {
    fun getCountryList(): List<Country> {
        return listOf(getCountry())
    }

    fun getCountry(): Country {
        return Country("id", "objectId", "name", 123, 123)
    }
}
