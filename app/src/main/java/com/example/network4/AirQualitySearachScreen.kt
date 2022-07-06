package com.example.network4

import android.app.Application
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.ContentLoadingProgressBar
import androidx.databinding.DataBindingUtil.setContentView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.network4.network.dto.AirQualityProvider
import com.example.network4.network.dto.AirQualityRepository
import com.example.network4.network.dto.RepoDto
import com.example.network4.network.dto.RepoResult
import com.google.android.material.snackbar.Snackbar

class AirQualitySearachScreen : AppCompatActivity(){


private lateinit var viewModel: MainActivityViewModel

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    viewModel =
        (application as MyApplication).mainActivityViewModelStarter.create(MainActivityViewModel::class.java)

    observeRepos()
    viewModel.send(GithubSearchEvent.RetrieveUserRepos("mrsasha"))

  }

  fun observeRepos() {
    val progress = findViewById<ContentLoadingProgressBar>(R.id.repo_loading_indicator)
    progress.show()

    viewModel.result.observe(this, {
        progress.hide()
        Log.d("MainActivity", "event: $it")

        when (it) {
            is AirQualitySearchViewmodelEvent.AirQualitySearchResult -> showRepos(it.repos)
            is AirQualitySearchViewmodelEvent.AirQualitySearchError -> Snackbar.make(
                findViewById(R.id.main_view),
                "Error retrieving repos: $it",
                Snackbar.LENGTH_INDEFINITE
            )
                .setAction("Retry") { viewModel.send(GithubSearchEvent.RetrieveUserRepos("mrsasha")) }
                .show()
            is AirQualitySearchViewmodelEvent.FirstTimeUser -> Toast.makeText(
                this,
                "Hi, nice to have you here!",
                Toast.LENGTH_LONG
            ).show()
        }
    })
}

  fun showRepos(repoResults: List<AirQualityRepository>) {
    Log.d("MainActivity", "list of repos received, size: ${repoResults.size}")

    val list = findViewById<RecyclerView>(R.id.repo_list)
    list.visibility = View.VISIBLE
    val adapter = RepoAdapter(repoResults)
    list.adapter = adapter
    list.layoutManager = LinearLayoutManager(this)
}
}