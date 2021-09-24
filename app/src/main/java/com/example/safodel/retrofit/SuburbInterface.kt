package com.example.safodel.retrofit

import com.example.safodel.model.*
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

    @GET("{key}?")
    fun streetRepos(@Path("key") key : String?,
                    @Query("suburb") suburb_name: String)
                    : Call<SuburbStreetsResponse>

    @GET("{key}?")
    suspend fun mapRepos(@Path("key") key : String?,
                    @Query("suburb") location_name: String)
            : SuburbMapResponse

    @GET("{key}")
    suspend fun alertsRepos(@Path("key") key: String?):SuburbAlertsResponse
}