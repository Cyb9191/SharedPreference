package com.example.network4

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.ContentLoadingProgressBar
import okhttp3.Interceptor
import retrofit2.Retrofit
import okhttp3.OkHttpClient
import retrofit2.http.GET
import retrofit2.http.Query
import kotlinx.coroutines.launch
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.converter.gson.GsonConverterFactory
import android.widget.TextView
import androidx.lifecycle.*
import com.example.network4.databinding.ActivityMainBinding

interface RecipesService {
    @GET("current/airquality")
    suspend fun getAirQuality(
        @Query("lat") recipe: String,
        @Query("lon") recipeb: String,
    ): Repo
}

const val E_BASE_URL = "https://air-quality.p.rapidapi.com/"
const val API_ID = "YourApiID"
const val API_KEY = "19e7b8759amshe6b93bd55c131c7p120076jsn06452ac17b99"
const val RAPIDAPI_KEY = "06a7614749msh19cff85cd7e9993p11616djsnda0c69ab3a46"
class MainActivityViewModel : ViewModel(){
    var listWeather=MutableLiveData<Repo>()
    var listError=MutableLiveData<Exception>()

    suspend fun UpdateWheater(recService:RecipesService):Boolean{
        try {
            //progress.show()
            var repos:Repo=recService.getAirQuality("40.71427", "-73.00597")
            listWeather.value=repos
            return true

        } catch (e: Exception) {
            Log.e("MainActivity", "error retrieving repos: $e")
            listError.value=e
            return false
        }}}

data class items(
    val mold_level: Int,
    val aqi: Int,
    val pm10: Number,
    val co: Number,
    val o3: Number,
    val predominant_pollen_type: String,
    val so2: Int,
    val pollen_level_tree: Int,
    val pollen_level_weed: Int,
    val no2: Number,
    val pm25: Number,
    val pollen_level_grass: Int
)

data class Repo(
    val city_name: String,
    val lon: Number,
    val timezone: String,
    val lat: Number,
    val country_code: String,
    val state_code: String,
    val data: Array<items>
)

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var repos:Repo
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setContentView(R.layout.activity_main)

        val mainActivityViewModel= ViewModelProvider(this).get(MainActivityViewModel::class.java)
        val firsttext = findViewById(R.id.textview_first) as TextView
        val errortext=findViewById(R.id.textview_error) as TextView
        val wheaterObserver= Observer<Repo> { newWheater ->
            firsttext.text = newWheater.city_name + "\n\n" + newWheater.lon.toString() +
                    "\n\n" + newWheater.timezone + "\n\n" + newWheater.lat.toString() + "\n\n" +
                    newWheater.country_code + "\n\n" + newWheater.state_code + "\n\n" + newWheater.data[0].mold_level +
                    "\n\n" + newWheater.data[0].aqi + "\n\n" + newWheater.data[0].pm10 + "\n\n" + newWheater.data[0].co +
                    "\n\n" + newWheater.data[0].o3 + "\n\n" + newWheater.data[0].predominant_pollen_type +
                    "\n\n" + newWheater.data[0].so2 + "\n\n" + newWheater.data[0].pollen_level_tree + "\n\n" +
                    newWheater.data[0].pollen_level_weed + "\n\n" + newWheater.data[0].no2 +
                    "\n\n" + newWheater.data[0].pm25 + "\n\n" + newWheater.data[0].pollen_level_grass
            errortext.text="No Error"
        }
        val errorObserver= Observer<Exception> { newError ->
            errortext.text = newError.toString()
            firsttext.text ="No Data"
        }
        mainActivityViewModel.listWeather.observe(this, wheaterObserver)
        mainActivityViewModel.listError.observe(this, errorObserver)
        retrieveRepos(mainActivityViewModel)
        }
    class HeaderInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
            val request = chain.request()
            val headers = request.headers
                .newBuilder()
                .add("X-RapidAPI-Key", RAPIDAPI_KEY)
                .build()

            val requestWithHeaders = request.newBuilder().headers(headers).build()
            return chain.proceed(requestWithHeaders)
        }
    }
    val logger = HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BASIC) }
    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(HeaderInterceptor())
        .build()

    val retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl("https://air-quality.p.rapidapi.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val recipesService = retrofit.create(RecipesService::class.java)


    fun retrieveRepos(vm:MainActivityViewModel) {
        val progress = findViewById<ContentLoadingProgressBar>(R.id.repo_loading_indicator)
        lifecycleScope.launch{

            var callingResult: Boolean = vm.UpdateWheater(recipesService)
            if (!callingResult){
                retrieveRepos(vm)
            }

        progress.hide()
    }

    fun showRepos(repoResults: Repo) {
        Log.d("MainActivity", "list of repos received, size: $repoResults")
        //firsttext.setVisibility=

    }
}}

