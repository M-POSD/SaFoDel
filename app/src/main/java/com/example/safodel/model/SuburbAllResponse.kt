package com.example.safodel.model

import com.google.gson.annotations.SerializedName

class SuburbAllResponse {
    @SerializedName("results")
    var suburbAllAccidents: SuburbAllAccidents = SuburbAllAccidents()
}