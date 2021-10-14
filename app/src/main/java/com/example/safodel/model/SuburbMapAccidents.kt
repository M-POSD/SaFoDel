package com.example.safodel.model

import com.google.gson.annotations.SerializedName

class SuburbMapAccidents {
    @SerializedName("accident_type_desc")
    var type: String = ""
    @SerializedName("full_road_name")
    var road_name: String = ""
    @SerializedName("accident_year")
    var year: Double = 0.0
    @SerializedName("location")
    var location: AccidentLocation = AccidentLocation()
    @SerializedName("inj_level_desc")
    var severity: String = ""
}