package com.example.safodel.model

import com.google.gson.annotations.SerializedName

class SuburbAlertsResponse {
    @SerializedName("results")
    var suburbAlertsAccidents: List<SuburbAlertsAccidents> = ArrayList()
}