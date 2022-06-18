package com.example.network4.ui

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.ContentLoadingProgressBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.network4.MainActivityViewModel
import com.example.network4.MyApplication
import com.example.network4.R
import com.example.network4.network.dto.RepoDto
import kotlinx.coroutines.launch

class AirQualitySearchScreen:AppCompatActivity(){
    val firsttext = findViewById(R.id.textview_first) as TextView
    val errortext=findViewById(R.id.textview_error) as TextView
    private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setContentView(R.layout.activity_main)
        viewModel =
            (application as MyApplication).mainActivityViewModelStarter.create(MainActivityViewModel::class.java)

        //ViewModelProvider(this).get(MainActivityViewModel::class.java)


        val wheatherObserver =Observer<RepoDto> { newWheater ->
                    firsttext.text = newWheater.city_name + "\n\n" + newWheater.lon.toString() +
                            "\n\n" + newWheater.timezone + "\n\n" + newWheater.lat.toString() + "\n\n" +
                            newWheater.country_code + "\n\n" + newWheater.state_code + "\n\n" + newWheater.data[0].mold_level +
                            "\n\n" + newWheater.data[0].aqi + "\n\n" + newWheater.data[0].pm10 + "\n\n" + newWheater.data[0].co +
                            "\n\n" + newWheater.data[0].o3 + "\n\n" + newWheater.data[0].predominant_pollen_type +
                            "\n\n" + newWheater.data[0].so2 + "\n\n" + newWheater.data[0].pollen_level_tree + "\n\n" +
                            newWheater.data[0].pollen_level_weed + "\n\n" + newWheater.data[0].no2 +
                            "\n\n" + newWheater.data[0].pm25 + "\n\n" + newWheater.data[0].pollen_level_grass
                    errortext.text = "No Error"
                }

        val errorObserver= Observer<Exception> { newError ->
                errortext.text = newError.toString()
                firsttext.text ="No Data"
            }
        viewModel.listWeather.observe(this, wheatherObserver)
        viewModel.listError.observe(this, errorObserver)

        }}