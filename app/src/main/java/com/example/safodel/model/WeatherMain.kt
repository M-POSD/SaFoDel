package com.example.safodel.model

import com.google.gson.annotations.SerializedName

/**
 * "main" key in "weather" dict from json data received
 */
class WeatherMain {
    @SerializedName("main")
    var main: String = ""
}