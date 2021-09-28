package com.example.safodel.model

import com.google.gson.annotations.SerializedName

/**
 * "Wind" key in WeatherResponse
 */
class Wind {
    // meter/s
    @SerializedName("speed")
    var speed = 0f
}