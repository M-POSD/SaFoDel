package com.example.safodel.model

import com.google.gson.annotations.SerializedName

class SuburbAllAccidents {
    @SerializedName("accidents")
    var accidents: List<SuburbMapAccidents> = ArrayList()
    @SerializedName("paths")
    var paths: List<SuburbPathsGeometry> = ArrayList()
    @SerializedName("alerts")
    var alerts: List<SuburbAlertsAccidents> = ArrayList()
}