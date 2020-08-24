package com.sergeyrodin.citiestask.data.source

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="countries")
data class Country(
    @PrimaryKey(autoGenerate=true)
    var id: Long = 0,
    var name: String
)