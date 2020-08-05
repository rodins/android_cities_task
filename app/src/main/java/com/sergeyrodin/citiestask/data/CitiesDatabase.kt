package com.sergeyrodin.citiestask.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Country::class, City::class], version = 1, exportSchema = false)
abstract class CitiesDatabase: RoomDatabase() {
    abstract val citiesDatabaseDao: CitiesDatabaseDao
}