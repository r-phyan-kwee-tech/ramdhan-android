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
        val countries: Countries,
        val country: Country,
        val state: State,
        val day: TimeTableDay,
        val states: States,
        val days: Days
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








