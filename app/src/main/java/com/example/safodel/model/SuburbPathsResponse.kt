package com.example.safodel.model

import com.google.gson.annotations.SerializedName

class SuburbPathsResponse {
    @SerializedName("results")
    var pathResults: List<SuburbPathsGeometry> = ArrayList()
}