package com.example.safodel.model

import com.google.gson.annotations.SerializedName

/**
 * "Main" key om WeatherResponse
 */
class Main {
    @SerializedName("humidity")
    var humidity = 0

    @SerializedName("pressure")
    var pressure = 0f

    // Celsius
    @SerializedName("temp")
    var temp = 0f
}