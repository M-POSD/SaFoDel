package com.example.safodel.model

import com.google.gson.annotations.SerializedName

class DirectionResponse {
    @SerializedName("routes")
    var directionResult: List<DirectionResult> = ArrayList()
}