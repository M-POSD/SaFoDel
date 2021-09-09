package com.example.safodel.model

import com.google.gson.annotations.SerializedName

class SuburbStreetsAccidents {

    @SerializedName("full_road_name")
    var accidentAddressName: String =  ""
    @SerializedName("accidents")
    var accidentsNumber: Int = 0
}