package com.marmutech.ramdantimetable.ramadantimetable

import com.marmutech.ramdantimetable.ramadantimetable.model.Country
import com.marmutech.ramdantimetable.ramadantimetable.model.State
import com.marmutech.ramdantimetable.ramadantimetable.model.TimeTableDay
import java.util.*

object TestUtil {
    fun createCountry(name: String) = Country(
            id = UUID.randomUUID().toString(),
            objectId = UUID.randomUUID().toString(),
            name = name,
            createdDate = 16253547,
            updatedDate = 82736473

    )

    fun createState(countryId: String) = State(
            id = UUID.randomUUID().toString(),
            objectId = UUID.randomUUID().toString(),
            nameMmUni = "မွန်ပြည်နယ်",
            nameMmZawgyi = "မွန်ပြည်နယ်",
            countryId = countryId,
            createdDate = 16253547,
            updatedDate = 82736473
    )

    fun createTimtableDay(day: Int, countryId: String, stateId: String) = TimeTableDay(
            id = UUID.randomUUID().toString(),
            objectId = UUID.randomUUID().toString(),
            countryId = countryId,
            stateId = stateId,
            calendarDay = day.toString(),
            createdDate = 7263543,
            day = day,
            dayMm = "၁",
            duaAr = "هذا لغرض العرض",
            duaEn = "This is for Demo Purpose",
            duaMmUni = "သီဟို",
            duaMmZawgyi = "သီဟို",
            hijariDay = "1389-9-23",
            iftariTime = "7:30pm",
            iftariTimeDesc = "Iftari",
            sehriTime = "4:30am",
            iftariTimeDescMmUni = "ဝါဖြေချိန်",
            iftariTimeDescMmZawgyi = "ဝါဖ့ေခိတ်",
            isKadir = false,
            sehriTimeDesc = "Sehri",
            sehriTimeDescMmUni = "ဝါချည်ေချိန်",
            sehriTimeDescMmZawgyi = "ဝါချည်ချိန်",
            updatedDate = 1237639

    )
}