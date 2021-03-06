package com.sergeyrodin.citiestask.info

import com.squareup.moshi.Json

data class CityInfo (
    var title: String = "",
    var summary: String = "",
    var latitude: String = "",
    var longitude: String = "",
    var thumbnailImg: String = ""
)