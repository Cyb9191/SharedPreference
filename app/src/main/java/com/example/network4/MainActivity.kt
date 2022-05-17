package com.example.network4
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.ContentLoadingProgressBar
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import okhttp3.Interceptor
import retrofit2.Retrofit
import okhttp3.OkHttpClient
import retrofit2.http.GET
import retrofit2.http.Query
import kotlinx.coroutines.launch
import okhttp3.logging.HttpLoggingInterceptor
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Path
import androidx.core.content.ContentProviderCompat.requireContext


import com.example.network4.*





const val E_BASE_URL = "https://air-quality.p.rapidapi.com"
const val API_ID = "YourApiID"
const val API_KEY = "19e7b8759amshe6b93bd55c131c7p120076jsn06452ac17b99"

//Here you add your url interceptor
//"app_id" and "app_key" might be different, depending on your API


//FINALLY build your retrofit



//You can now declare your interfaces with your REST methods as usual, for example GET which will return your object



class RepoResult : ArrayList<Repo>()

data class Repo(
    val mold_level:Int,
    val aqi:Int,
    val pm10:Number,
    val co:Number,
    val o3: Number,
    val predominant_pollen_type: String,
    val so2: Int,
    val pollen_level_tree: Int,
    val pollen_level_weed:Int,
    val no2:Number,
    val pm25: Number,
    val pollen_level_grass:Int,
    val city_name: String,
    val lon: Number,
    val timezone: String,
    val lat:Number,
    val country_code: String,
    val state_code: String

)





class MainActivity : AppCompatActivity() {
    interface RecipesService {
        @GET("client")
        suspend fun getRecipes(@Query("client") recipe: String): RepoResult
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        retrieveRepos()


    }


//.addQueryParameter("app_id", API_ID)
//Finally you create your object (Singleton in Java) which generates your service via lazy delegate
    val api_interceptor = Interceptor {
        val originalRequest = it.request()
        val newHttpUrl = originalRequest.url.newBuilder()

            .addQueryParameter("app_key", API_KEY)
            .build()
        val newRequest = originalRequest.newBuilder()
            .url(newHttpUrl)
            .build()
        it.proceed(newRequest)
    }

    //Add the logger interceptor optional:
    val logger = HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BASIC) }

    //Build your OkHttpClient - here you add the api_interceptor and logger
    val clientHTTP = OkHttpClient().newBuilder()
        .addNetworkInterceptor(logger)  //optional
        .addNetworkInterceptor(api_interceptor)
        .build()

    //Build your json converter - in this example MOSHI
    val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()


    //FINALLY build your retrofit
    val retrofitE = Retrofit.Builder()
        .client(clientHTTP)
        .baseUrl(E_BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()
    fun retrieveRepos() {
        val progress = findViewById<ContentLoadingProgressBar>(R.id.repo_loading_indicator)

        lifecycleScope.launch {
            try {
                progress.show()
                val repos = RecipesService.getRecipes(clientHTTP)
                progress.hide()
                showRepos(repos)
            } catch (e: Exception) {
                Log.e("MainActivity", "error retrieving repos: $e")
                progress.hide()
                Snackbar.make(
                    findViewById(R.id.main_view),
                    "Error retrieving repos",
                    Snackbar.LENGTH_INDEFINITE
                ).setAction("Retry") { retrieveRepos() }.show()
            }
        }
    }

    //You can now declare your interfaces with your REST methods as usual, for example GET which will return your object








    //Finally you create your object (Singleton in Java) which generates your service via lazy delegate





    fun showRepos(repoResults: List<Repo>) {
        Log.d("MainActivity", "list of repos received, size: ${repoResults.size}")

        val list = findViewById<RecyclerView>(R.id.repo_list)
        list.visibility = View.VISIBLE
        val adapter = RepoAdapter(repoResults)
        list.adapter = adapter
        list.layoutManager = LinearLayoutManager(applicationContext)
    }
}