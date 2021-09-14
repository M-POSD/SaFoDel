package com.example.safodel.model

import com.google.gson.annotations.SerializedName

class SuburbMapAccidents {

    @SerializedName("lat")
    var point_lat: Double = 0.0
    @SerializedName("long")
    var point_long: Double = 0.0
}