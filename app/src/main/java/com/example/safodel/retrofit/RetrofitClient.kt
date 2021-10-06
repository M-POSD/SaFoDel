package com.example.safodel.retrofit

import retrofit2.converter.gson.GsonConverterFactory

import retrofit2.Retrofit


class RetrofitClient {

    companion object RetrofitManager {
        private const val BASE_URL = "https://api.openweathermap.org/"

        fun getRetrofitService(): RetrofitInterface {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(RetrofitInterface::class.java)
        }
    }
}