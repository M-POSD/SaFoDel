package com.example.safodel.model

import com.google.gson.annotations.SerializedName

class SuburbPathsGeometry {
    @SerializedName("geometry")
    var geometries: List<SuburbPathsLatLong> = ArrayList()
}