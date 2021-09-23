package com.example.safodel.model

import com.google.gson.annotations.SerializedName

class AccidentLocation {
    @SerializedName("lat")
    var lat: Double = 0.0

    @SerializedName("long")
    var long: Double = 0.0
}