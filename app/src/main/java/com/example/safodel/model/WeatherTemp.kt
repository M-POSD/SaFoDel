package com.example.safodel.model

/**
 * For pass the weather object between viewModel
 */
data class WeatherTemp(
    var location: String,
    var weather: String,
    var temp: Float, // celsius
    var pressure: Float, //hPa
    var humidity: Int, // %
    var windSpeed: Float // m/s -> miles/hr
)
