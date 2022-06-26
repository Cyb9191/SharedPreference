package com.example.network4.network.dto


import retrofit2.http.GET
import retrofit2.http.Query

interface RecipesService {
    @GET("current/airquality")
    suspend fun getAirQuality(
        @Query("lat") recipe: String,
        @Query("lon") recipeb: String,
    ): RepoDto
}