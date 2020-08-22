package com.sergeyrodin.citiestask.info.remote

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class CityInfoConverterDataSourceTest{
    private lateinit var dataSource: GeoNamesFakeDataSource
    private lateinit var subject: CityInfoConverterDataSource

    @Before
    fun initDataSource() {
        dataSource = GeoNamesFakeDataSource()
        subject = CityInfoConverterDataSource(dataSource)
    }

    @Test
    fun featureCityItems_nameEquals() = runBlockingTest{
        val item1 = GeonamesItem("CityItem1", "Country")
        val item2 = GeonamesItem("CityItem2", "Country")
        val item3 = GeonamesItem("City1", "Country", "city")
        val item4 = GeonamesItem("CityItems3", "Country")
        dataSource.addGeoNames(item1, item2, item3, item4)

        val cityInfo = subject.fetchCityInfo("Country", "City")
        assertThat(cityInfo.title, `is`(item3.title))
    }

    @Test
    fun noFeatureCity_cityNameFirst_nameEquals() = runBlockingTest{
        val item1 = GeonamesItem("Item1City", "Country")
        val item2 = GeonamesItem("Item2City", "Country")
        val item3 = GeonamesItem("CityItem", "Country")
        val item4 = GeonamesItem("Items3City", "Country")
        dataSource.addGeoNames(item1, item2, item3, item4)

        val cityInfo = subject.fetchCityInfo("Country", "City")
        assertThat(cityInfo.title, `is`(item3.title))
    }

    @Test
    fun cityNameEqualsGeonamesItemTitle() = runBlockingTest{
        val item1 = GeonamesItem("ItemCity1", "Country")
        val item2 = GeonamesItem("ItemCity2", "Country", feature = "city")
        val item3 = GeonamesItem("CityItem", "Country")
        val item4 = GeonamesItem("City", "Country")
        dataSource.addGeoNames(item1, item2, item3, item4)

        val cityInfo = subject.fetchCityInfo("Country", "City")
        assertThat(cityInfo.title, `is`(item4.title))
    }
}