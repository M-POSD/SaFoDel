package com.example.safodel.retrofit

import com.example.safodel.model.DirectionResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DirectionInterface {

    @GET("{key}?")
    fun directionRepos(
        @Path("key") key: String?,
        @Query("geometries") geometries: String,
        @Query("access_token") access_token: String
    )
            : Call<DirectionResponse>
}