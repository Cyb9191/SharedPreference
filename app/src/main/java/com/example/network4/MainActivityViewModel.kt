package com.example.network4

import android.util.Log
import androidx.core.widget.ContentLoadingProgressBar
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import com.example.network4.network.dto.AirQualityProvider
import com.example.network4.network.dto.RecipesService
import com.example.network4.network.dto.RepoDto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivityViewModel(val secondAirQualityProvider: AirQualityProvider): ViewModel(){
    var listWeather= MutableLiveData<RepoDto>()
    var listError= MutableLiveData<Exception>()


    suspend fun UpdateWheater(recService: RecipesService):Boolean{
        try {
            //progress.show()
            var repos:RepoDto=recService.getAirQuality("40.71427", "-73.00597")
            listWeather.value=repos
            return true

        } catch (e: Exception) {
            Log.e("MainActivity", "error retrieving repos: $e")
            listError.value=e
            return false
        }}
    fun retrieveRepos(vm:MainActivityViewModel) {

        CoroutineScope(Dispatchers.Main).launch {
            var callingResult: Boolean = vm.UpdateWheater(secondAirQualityProvider.recipesService)
            if (!callingResult) {
                retrieveRepos(vm)
            }


        }

    }}
