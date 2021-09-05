package com.example.safodel.model

import com.google.gson.annotations.SerializedName

class SuburbResponse {
    @SerializedName("results")
    var suburbAccidents: List<SuburbAccidents> = ArrayList()
}