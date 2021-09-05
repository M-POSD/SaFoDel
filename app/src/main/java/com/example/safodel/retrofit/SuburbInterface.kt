package com.example.safodel.retrofit

import com.example.safodel.model.SuburbResponse
import com.example.safodel.model.SuburbTimeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SuburbInterface {
    @GET("{key}?")
    fun totalRepos(@Path("key") key: String?,
                  @Query("suburb") suburb_name: String)
                : Call<SuburbResponse>

    @GET("{key}?")
    fun timeRepos(@Path("key") key: String?,
                  @Query("suburb") suburb_name: String)
            : Call<SuburbTimeResponse>

}