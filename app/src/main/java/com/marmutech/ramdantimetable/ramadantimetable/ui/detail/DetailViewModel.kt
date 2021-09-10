package com.marmutech.ramdantimetable.ramadantimetable.ui.detail

import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.marmutech.ramdantimetable.ramadantimetable.model.TimeTableDay
import kotlinx.android.parcel.Parcelize
import timber.log.Timber
import kotlin.properties.Delegates

class DetailViewModel : ViewModel() {

    private var timeTableDay by Delegates.notNull<TimeTableDay>()

    private val _uiModel = MutableLiveData<DetailUiModel>()
    val uiModel: LiveData<DetailUiModel> get() = _uiModel

    fun onViewCreated(data: TimeTableDay) {
        this.timeTableDay = data
        Timber.d("timeTableDay $timeTableDay")
        _uiModel.value = mapTimeTableDayToUiModel(timeTableDay)
    }

    private fun mapTimeTableDayToUiModel(timeTableDay: TimeTableDay) = DetailUiModel(
        mapHeaderData(timeTableDay),
        mapTranslatedDuaList(timeTableDay)
    )


    private fun mapHeaderData(timeTableDay: TimeTableDay) = HeaderData(
        timeTableDay.day,
        timeTableDay.calendarDay,
        timeTableDay.sehriTime,
        timeTableDay.iftariTime
    )

    private fun mapTranslatedDuaList(timeTableDay: TimeTableDay): List<DuaTranslation> {
        return listOf(
            MMDuaTranslation(timeTableDay.duaMmUni),
            ENDuaTranslation(timeTableDay.duaEn),
            ARDuaTranslation(timeTableDay.duaAr)
        )
    }

}

data class DetailUiModel(
    val headerData: HeaderData,
    val translatedDuaList: List<DuaTranslation>
)

data class HeaderData(
    val day: Int,
    val calenderDay: String,
    val sehriTime: String,
    val iftariTime: String
)


class MMDuaTranslation(override val translation: String) : DuaTranslation {
    override val languageCode: LanguageCode
        get() = LanguageCode.MM
}

class ENDuaTranslation(override val translation: String) : DuaTranslation {
    override val languageCode: LanguageCode
        get() = LanguageCode.EN
}

class ARDuaTranslation(override val translation: String) : DuaTranslation {
    override val languageCode: LanguageCode
        get() = LanguageCode.AR
}

interface DuaTranslation {
    val languageCode: LanguageCode
    val translation: String
}

@Parcelize
enum class LanguageCode : Parcelable {
    EN, MM, AR
}
