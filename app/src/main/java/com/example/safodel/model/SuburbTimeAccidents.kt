package com.example.safodel.model

import com.google.gson.annotations.SerializedName

class SuburbTimeAccidents {
    @SerializedName("accident_hour")
    var accidentHours: Int = 0
    @SerializedName("accidents")
    var accidentsNumber: Int = 0
}