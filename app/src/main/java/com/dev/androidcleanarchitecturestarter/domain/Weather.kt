package com.dev.androidcleanarchitecturestarter.domain

import com.dev.androidcleanarchitecturestarter.data.WeatherDTO

data class Weather(
    val coordinates: Coordinates,
    val elevation: Double,
    val timezone: String,
    val hourlyForecast: List<HourlyForecast>
)

data class Coordinates(val latitude: Double, val longitude: Double)

data class HourlyForecast(
    val time: String,                  // or LocalDateTime once you parse it
    val temperatureC: Double,
    val precipitationProbability: Int,
    val windSpeedKmh: Double
)

fun WeatherDTO.toDomain(): Weather = Weather(
    coordinates = Coordinates(latitude = latitude, longitude = longitude),
    elevation = elevation,
    timezone = timezone,
    hourlyForecast = hourly.time.indices.map { i ->
        HourlyForecast(
            time = hourly.time[i],
            temperatureC = hourly.temperature_2m[i],
            precipitationProbability = hourly.precipitation_probability[i],
            windSpeedKmh = hourly.wind_speed_10m[i]
        )
    }
)