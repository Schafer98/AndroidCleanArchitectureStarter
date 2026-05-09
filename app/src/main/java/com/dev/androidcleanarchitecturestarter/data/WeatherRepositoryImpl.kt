package com.dev.androidcleanarchitecturestarter.data

import com.dev.androidcleanarchitecturestarter.domain.Weather
import com.dev.androidcleanarchitecturestarter.domain.WeatherRepository
import com.dev.androidcleanarchitecturestarter.domain.toDomain
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherAPI
) : WeatherRepository {
    override suspend fun getForecast(
        latitude: Double,
        longitude: Double
    ): Result<Weather> = runCatching {
        api.getForecast(latitude, longitude).toDomain()
    }
}

