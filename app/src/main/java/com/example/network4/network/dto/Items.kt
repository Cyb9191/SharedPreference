package com.example.network4.network.dto

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