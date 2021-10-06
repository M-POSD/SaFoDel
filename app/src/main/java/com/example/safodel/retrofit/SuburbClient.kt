package com.example.safodel.retrofit

import retrofit2.converter.gson.GsonConverterFactory

import retrofit2.Retrofit


class SuburbClient {

    companion object RetrofitManager {
        private const val BASE_URL = "http://13.54.41.119"

        fun getSuburbService(): SuburbInterface {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(SuburbInterface::class.java)
        }
    }
}