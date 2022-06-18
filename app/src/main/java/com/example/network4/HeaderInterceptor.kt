package com.example.network4

import com.example.network4.network.dto.LicenseDto
import okhttp3.Interceptor

class HeaderInterceptor : Interceptor {
    val LicenseAirQuality= LicenseDto(
     "https://air-quality.p.rapidapi.com/",
     "YourApiID",
     "19e7b8759amshe6b93bd55c131c7p120076jsn06452ac17b99",
     "06a7614749msh19cff85cd7e9993p11616djsnda0c69ab3a46"
    )
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val request = chain.request()
        val headers = request.headers
            .newBuilder()
            .add("X-RapidAPI-Key", LicenseAirQuality.RAPIDAPI_KEY)
            .build()

        val requestWithHeaders = request.newBuilder().headers(headers).build()
        return chain.proceed(requestWithHeaders)
    }
}