package com.example.network4

import android.content.SharedPreferences
import android.util.Log
import androidx.core.widget.ContentLoadingProgressBar
import androidx.lifecycle.*
import com.example.network4.network.dto.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
sealed class GithubSearchEvent {
    data class RetrieveUserRepos(val name: String) : GithubSearchEvent()
}
val coordinate1:String="40.71427"
val coordinate2:String="-73.00597"
sealed class AirQualitySearchViewmodelEvent {
    data class AirQualitySearchResult(val repos: List<AirQualityRepository>) : AirQualitySearchViewmodelEvent()
    data class AirQualitySearchError(val message: String) : AirQualitySearchViewmodelEvent()
    object FirstTimeUser : AirQualitySearchViewmodelEvent()
}

const val KEY_FIRST_TIME_USER = "first_time_user"

class MainActivityViewModel(val secondAirQualityProvider: AirQualityProvider,private val preferences: SharedPreferences): ViewModel(){
    var listWeather= MutableLiveData<RepoResult>()
    var listError= MutableLiveData<Exception>()
    private var _result = MutableLiveData<AirQualitySearchViewmodelEvent>()
    val result: LiveData<AirQualitySearchViewmodelEvent>
        get() = _result
    init {
        Log.d("MainVM", "init")
        checkFirstTimeUser(preferences)
    }

    private fun checkFirstTimeUser(preferences: SharedPreferences) {
        val firstTimeUser = preferences.getBoolean(KEY_FIRST_TIME_USER, true)
        Log.d("MainVM", "firstTimeUser: ${firstTimeUser}")

        if (firstTimeUser) {
            preferences.edit().putBoolean(KEY_FIRST_TIME_USER, false).apply()
            _result.value = AirQualitySearchViewmodelEvent.FirstTimeUser
        }
    }

    fun send(event: GithubSearchEvent) =
        when (event) {
            is GithubSearchEvent.RetrieveUserRepos -> retrieveRepos(event.name)
        }

//,private val preferences: SharedPreferences
    suspend fun UpdateWheater(recService: RecipesService):Boolean{
        try {
            //progress.show()
            var repos:RepoResult=recService.getAirQuality("40.71427", "-73.00597",PAGE_SIZE)
            listWeather.value=repos
            return true

        } catch (e: Exception) {
            Log.e("MainActivity", "error retrieving repos: $e")
            listError.value=e
            return false
        }}
    fun retrieveRepos(username: String) {
        Log.d("MainViewModel", "retrieveRepos")
        CoroutineScope(Dispatchers.Main).launch {
            try {
                _result.value = AirQualitySearchViewmodelEvent.AirQualitySearchResult(secondAirQualityProvider.GetUserRepos(
                    coordinate1, coordinate2))
            } catch (e: Exception) {
                _result.value =
                    AirQualitySearchViewmodelEvent.AirQualitySearchError("error retrieving user repos: ${e.localizedMessage}")
            }

    }}}
