package com.sergeyrodin.citiestask.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="countries")
data class Country(
    @PrimaryKey(autoGenerate=true)
    var id: Int = 0,
    var name: String
)