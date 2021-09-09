package com.example.safodel.retrofit

import com.example.safodel.model.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitInterface {
    @GET("data/2.5/weather?")
    fun getCurrentWeatherData(
        @Query("q") city_name: String,
        @Query("appid") app_id: String
    ): Call<WeatherResponse>
}