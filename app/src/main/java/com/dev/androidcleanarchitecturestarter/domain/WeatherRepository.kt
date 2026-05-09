package com.dev.androidcleanarchitecturestarter.domain

interface WeatherRepository {
    suspend fun getForecast(latitude: Double, longitude: Double): Result<Weather>
}