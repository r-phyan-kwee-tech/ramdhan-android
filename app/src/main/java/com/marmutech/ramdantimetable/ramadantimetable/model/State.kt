package com.marmutech.ramdantimetable.ramadantimetable.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index

@Entity(tableName = "state", primaryKeys = ["objectId"], indices = [Index("objectId"), Index("countryId")])
data class State(
    @ColumnInfo(name = "id")
        val id: String,
    @ColumnInfo(name = "objectId")
        val objectId: String,
    @ColumnInfo(name = "nameMmUni")
    val nameMmUni: String,
    @ColumnInfo(name = "nameMmZawgyi")
    val nameMmZawgyi: String,
    @ColumnInfo(name = "countryId")
    val countryId: String,
    @ColumnInfo(name = "createdDate")
    val createdDate: Int,
    @ColumnInfo(name = "updatedDate")
    val updatedDate: Int

)
