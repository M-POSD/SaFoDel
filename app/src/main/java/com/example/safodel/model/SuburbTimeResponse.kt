package com.example.safodel.model

import com.google.gson.annotations.SerializedName

class SuburbTimeResponse {
    @SerializedName("results")
    var suburbTimeAccidents: List<SuburbTimeAccidents> = ArrayList()
}