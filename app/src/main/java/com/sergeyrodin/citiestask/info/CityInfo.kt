package com.sergeyrodin.citiestask.info

import com.squareup.moshi.Json

data class CityInfo (
    var title: String = "",
    var summary: String = "",
    @Json(name = "lat")
    var latitude: String = "",
    @Json(name = "lng")
    var longitude: String = "",
    var thumbnailImg: String = ""
)