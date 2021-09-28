package com.example.safodel.model

import com.google.gson.annotations.SerializedName

/**
 * get the key(weather) from json data received
 */
class WeatherResponse {
    @SerializedName("weather")
    var weatherMainList: List<WeatherMain> = ArrayList()

    @SerializedName("main")
    var main = Main()

    @SerializedName("wind")
    var wind = Wind()
}