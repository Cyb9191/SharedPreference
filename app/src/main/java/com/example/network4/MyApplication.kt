package com.example.network4

import android.util.Log
import com.example.network4.network.dto.AirQualityProvider
import android.app.Application
import androidx.lifecycle.ViewModelProvider
import com.example.network4.network.dto.LicenseDto

class MyApplication:Application() {
    private val airQualityProvider = AirQualityProvider()
    val mainActivityViewModel = MyViewModelFactory(airQualityProvider)
    //object LicenseAirQuality: LicenseDto {
       // E_BASE_URL : "https://air-quality.p.rapidapi.com/",
      //  API_ID = "YourApiID",
       // API_KEY = "19e7b8759amshe6b93bd55c131c7p120076jsn06452ac17b99",
       // RAPIDAPI_KEY = "06a7614749msh19cff85cd7e9993p11616djsnda0c69ab3a46"
    //}

    override fun onCreate() {
        super.onCreate()
        Log.d("MyAppliction", "started")
    }
}
