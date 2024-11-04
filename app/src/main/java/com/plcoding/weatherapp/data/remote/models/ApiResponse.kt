package com.plcoding.weatherapp.data.remote.models

data class ApiResponse(
    val latitude: Double,
    val longitude: Double,
    val generationtime_ms: Double,
    val utc_offset_seconds: Int,
    val timezone: String,
    val timezone_abbreviation: Int,
    val elevation: Double,
    val hourly_units: HourlyUnits,
    val hourly: Hourly,
)
