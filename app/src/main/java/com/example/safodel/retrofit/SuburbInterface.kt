package com.example.safodel.retrofit

import com.example.safodel.model.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SuburbInterface {

    @GET("{key}?")
    fun streetRepos(
        @Path("key") key: String?,
        @Query("suburb") suburb_name: String
    )
            : Call<SuburbStreetsResponse>

    @GET("{key}")
    fun allRepos(
        @Path("key") key: String?,
        @Query("suburb") suburb_name: String
    )
            : Call<SuburbAllResponse>
}