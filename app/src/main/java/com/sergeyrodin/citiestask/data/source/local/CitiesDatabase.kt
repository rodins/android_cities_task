package com.sergeyrodin.citiestask.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sergeyrodin.citiestask.data.source.City
import com.sergeyrodin.citiestask.data.source.Country

@Database(entities = [Country::class, City::class], version = 1, exportSchema = false)
abstract class CitiesDatabase: RoomDatabase() {
    abstract val citiesDatabaseDao: CitiesDatabaseDao
}