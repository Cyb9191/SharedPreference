package com.example.network4

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.network4.network.dto.AirQualityProvider
import com.example.network4.MainActivityViewModel

class MyViewModelFactory (private val thirdAirQualityProvider: AirQualityProvider) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainActivityViewModel::class.java)) {
                return MainActivityViewModel(thirdAirQualityProvider) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
