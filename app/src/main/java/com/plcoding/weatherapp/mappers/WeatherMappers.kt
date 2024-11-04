package com.plcoding.weatherapp.mappers

import com.plcoding.weatherapp.data.remote.models.ApiResponse
import com.plcoding.weatherapp.data.remote.models.Hourly
import com.plcoding.weatherapp.domain.weather.WeatherData
import com.plcoding.weatherapp.domain.weather.WeatherInfo
import com.plcoding.weatherapp.domain.weather.WeatherType
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private data class IndexedWeatherData(
    val index: Int,
    val data: WeatherData
)

fun Hourly.toWeatherDataMap(): Map<Int, List<WeatherData>>{
    return time.mapIndexed { index, time ->
        val temperature = temperature_2m[index]
        val weatherCode = weathercode[index]
        val windSpeed = windspeed_10m[index]
        val pressure = pressure_msl[index]
        val humidity = relativehumidity_2m[index]
        IndexedWeatherData(
            index = index,
            data = WeatherData(
                time = LocalDateTime.parse(time, DateTimeFormatter.ISO_DATE_TIME),
                temperatureCelsius = temperature,
                pressure =  pressure,
                windSpeed = windSpeed,
                humidity = humidity,
                weatherType = WeatherType.fromWMO(weatherCode)
            )
        )
    }.groupBy { it.index / 24  }.mapValues {
        it.value.map {
            it.data
        }
    }
}

fun ApiResponse.toWeatherInfo():WeatherInfo {
    val weatherDataMap = hourly.toWeatherDataMap()
    val now = LocalDateTime.now()
    val currentWeatherData = weatherDataMap[0]?.find {
        val hour = if (now.minute < 30) now.hour else now.hour+1
        it.time.hour == hour
    }
    return WeatherInfo(
        weatherDataPerDay = weatherDataMap,
        currentWeatherData = currentWeatherData
    )
}
