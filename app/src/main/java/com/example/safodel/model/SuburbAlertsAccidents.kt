package com.example.safodel.model

import com.google.gson.annotations.SerializedName

class SuburbAlertsAccidents {
    @SerializedName("incident_type")
    var type: String = ""
    @SerializedName("location")
    var location: AccidentLocation = AccidentLocation()
}