package com.plcoding.weatherapp.data.remote.models

data class Hourly(
    val time: List<String>,
    val temperature_2m: List<Double>,
    val weathercode: List<Int>,
    val relativehumidity_2m: List<Int>,
    val windspeed_10m: List<Double>,
    val pressure_msl: List<Double>
)
