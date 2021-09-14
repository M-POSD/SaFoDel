package com.example.safodel.model

import com.google.gson.annotations.SerializedName

class SuburbMapResponse {
    @SerializedName("results")
    var suburbMapAccidents: List<SuburbMapAccidents> = ArrayList()
}