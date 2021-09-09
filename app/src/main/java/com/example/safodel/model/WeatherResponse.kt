package com.example.safodel.model

import com.google.gson.annotations.SerializedName


class WeatherResponse {
    @SerializedName("weather")
    var weatherList: List<Weather> = ArrayList()
}