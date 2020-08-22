package com.sergeyrodin.citiestask.info.remote

import com.sergeyrodin.citiestask.info.CityInfo
import com.squareup.moshi.Json

data class GeoNames(
    @Json(name = "geonames")
    var geoNames: List<GeonamesItem>
)
