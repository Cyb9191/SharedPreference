package com.example.network4.network.dto


import com.example.network4.HeaderInterceptor
import com.example.network4.MyApplication

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AirQualityProvider {

    val okHttpClient={
        OkHttpClient.Builder()
        .addInterceptor(MyApplication,HeaderInterceptor())
        .build()
    }
    val retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl("https://air-quality.p.rapidapi.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val recipesService = retrofit.create(RecipesService::class.java)
    fun returnRecipesSerevice(): RecipesService {
        return(recipesService)
    }
}