package com.dev.androidcleanarchitecturestarter.data

data class WeatherDTO(
    val elevation: Double,
    val generationtime_ms: Double,
    val hourly: HourlyDTO,
    val hourly_units: HourlyUnitsDTO,
    val latitude: Double,
    val longitude: Double,
    val timezone: String,
    val timezone_abbreviation: String,
    val utc_offset_seconds: Int
)

data class HourlyDTO(
    val precipitation_probability: List<Int>,
    val temperature_2m: List<Double>,
    val time: List<String>,
    val wind_speed_10m: List<Double>
)

data class HourlyUnitsDTO(
    val precipitation_probability: String,
    val temperature_2m: String,
    val time: String,
    val wind_speed_10m: String
)