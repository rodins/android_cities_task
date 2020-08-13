package com.sergeyrodin.citiestask.data.source.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(tableName = "cities",
foreignKeys = arrayOf(ForeignKey(
    entity = Country::class,
    parentColumns = arrayOf("id"),
    childColumns = arrayOf("country_id"),
    onDelete = CASCADE
)))
data class City (
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var name: String,
    @ColumnInfo(name = "country_id", index = true)
    var countryId: Long
)