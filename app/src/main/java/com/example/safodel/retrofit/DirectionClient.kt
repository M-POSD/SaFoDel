package com.example.safodel.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DirectionClient {

    companion object RetrofitManager {
        private const val BASE_URL = "https://api.mapbox.com/directions/v5/mapbox/cycling/"

        fun getDirectionService(): DirectionInterface {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(DirectionInterface::class.java)
        }
    }
}