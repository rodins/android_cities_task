package com.sergeyrodin.citiestask.data

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

class CitiesDefaultRepositoryTest{

    private lateinit var dataSource: FakeDataSource
    private lateinit var subject: CitiesDefaultRepository

    @Before
    fun initDataSource() {
        dataSource = FakeDataSource()
        subject = CitiesDefaultRepository(dataSource, dataSource)
    }
}