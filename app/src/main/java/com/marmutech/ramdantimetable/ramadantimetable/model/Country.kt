package com.marmutech.ramdantimetable.ramadantimetable.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index


@Entity(tableName = "country", primaryKeys = ["objectId"], indices = [Index("objectId")])
data class Country(
    @ColumnInfo(name = "id")
    val id: String,
    @ColumnInfo(name = "objectId")
    val objectId: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "createdDate")
    val createdDate: Int,
    @ColumnInfo(name = "updatedDate")
    val updatedDate: Int
)
