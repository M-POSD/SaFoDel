package com.example.safodel.model

import com.google.gson.annotations.SerializedName

/**
 * get the key(weather) from json data received
 */
class WeatherResponse {
    @SerializedName("weather")
    var weatherList: List<Weather> = ArrayList()
}