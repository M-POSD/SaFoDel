package com.example.safodel.model

import com.google.gson.annotations.SerializedName

class SuburbStreetsResponse {

    @SerializedName("results")
    var suburbStreetsAccidents: List<SuburbStreetsAccidents> = ArrayList()

}