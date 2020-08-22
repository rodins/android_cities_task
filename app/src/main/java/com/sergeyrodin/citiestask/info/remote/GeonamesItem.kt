package com.sergeyrodin.citiestask.info.remote

import com.squareup.moshi.Json

data class GeonamesItem(
    var title: String = "",
    var summary: String = "",
    var feature: String = "",
    @Json(name = "lat")
    var latitude: String = "",
    @Json(name = "lng")
    var longitude: String = "",
    var thumbnailImg: String = ""
)
