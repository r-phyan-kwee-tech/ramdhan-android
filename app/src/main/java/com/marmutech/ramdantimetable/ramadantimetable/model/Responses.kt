package com.marmutech.ramdantimetable.ramadantimetable.model


data class CountryResponse(
    val data: Data
)

data class StateResponse(
    val data: Data
)

data class DayResponse(
    val data: Data
)

data class Data(
    val countries: Countries? = null,
    val country: Country? = null,
    val state: State? = null,
    val day: TimeTableDay? = null,
    val states: States? = null,
    val days: Days? = null
)


data class Days(
    val data: List<TimeTableDay>
)

data class Countries(
    val data: List<Country>
)

data class States(
    val data: List<State>
)








