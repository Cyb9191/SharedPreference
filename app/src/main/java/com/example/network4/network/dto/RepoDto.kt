package com.example.network4.network.dto



data class RepoDto(
    val city_name: String,
    val lon: Number,
    val timezone: String,
    val lat: Number,
    val country_code: String,
    val state_code: String,
    val data: Array<items>
)