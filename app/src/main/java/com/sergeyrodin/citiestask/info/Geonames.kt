package com.sergeyrodin.citiestask.info

import com.squareup.moshi.Json

data class GeoNames(
    @Json(name = "geonames")
    var geoNames: List<CityInfo>
)
