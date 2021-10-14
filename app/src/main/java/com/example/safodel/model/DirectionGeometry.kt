package com.example.safodel.model

import com.google.gson.annotations.SerializedName

class DirectionGeometry {
    @SerializedName("coordinates")
    var coordinates: List<DoubleArray> = ArrayList()

    @SerializedName("type")
    var type: String = "LineString"
}