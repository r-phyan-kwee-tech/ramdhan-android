package com.marmutech.ramdantimetable.ramadantimetable.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index


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
) {
    override fun toString(): String = name
}