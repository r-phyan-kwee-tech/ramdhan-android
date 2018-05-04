package com.marmutech.ramdantimetable.ramadantimetable.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index

@Entity(tableName = "day", primaryKeys = ["objectId"], indices = [
    Index("objectId"),
    Index("countryId"),
    Index("stateId")])
data class TimeTableDay(

        @ColumnInfo(name = "id")
        val id: String,
        @ColumnInfo(name = "objectId")
        val objectId: String,
        @ColumnInfo(name = "day")
        val day: Int,
        @ColumnInfo(name = "dayMm")
        val dayMm: String,
        @ColumnInfo(name = "calendarDay")
        val calendarDay: String,
        @ColumnInfo(name = "hijariDay")
        val hijariDay: String,
        @ColumnInfo(name = "sehriTime")
        val sehriTime: String,
        @ColumnInfo(name = "iftariTime")
        val iftariTime: String,
        @ColumnInfo(name = "sehriTimeDesc")
        val sehriTimeDesc: String,
        @ColumnInfo(name = "iftariTimeDesc")
        val iftariTimeDesc: String,
        @ColumnInfo(name = "sehriTimeDescMmUni")
        val sehriTimeDescMmUni: String,
        @ColumnInfo(name = "sehriTimeDescMmZawgyi")
        val sehriTimeDescMmZawgyi: String,
        @ColumnInfo(name = "iftariTimeDescMmZawgyi")
        val iftariTimeDescMmZawgyi: String,
        @ColumnInfo(name = "iftariTimeDescMmUni")
        val iftariTimeDescMmUni: String,
        @ColumnInfo(name = "isKadir")
        val isKadir: Boolean,
        @ColumnInfo(name = "duaAr")
        val duaAr: String,
        @ColumnInfo(name = "duaEn")
        val duaEn: String,
        @ColumnInfo(name = "duaMmUni")
        val duaMmUni: String,
        @ColumnInfo(name = "duaMmZawgyi")
        val duaMmZawgyi: String,
        @ColumnInfo(name = "countryId")
        val countryId: String,
        @ColumnInfo(name = "stateId")
        val stateId: String,
        @ColumnInfo(name = "createdDate")
        val createdDate: Int,
        @ColumnInfo(name = "updatedDate")
        val updatedDate: Int
)