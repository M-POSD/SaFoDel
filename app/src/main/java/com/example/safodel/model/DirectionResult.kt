package com.example.safodel.model

import com.google.gson.annotations.SerializedName
import com.mapbox.geojson.Geometry


class DirectionResult {
    @SerializedName("geometry")
    var geometry: DirectionGeometry = DirectionGeometry()
}