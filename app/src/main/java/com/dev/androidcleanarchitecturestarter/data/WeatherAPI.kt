package com.dev.androidcleanarchitecturestarter.data

import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {

    @GET("v1/forecast")
    suspend fun getForecast(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("hourly") hourly: String =
            "temperature_2m,wind_speed_10m,precipitation_probability",
        @Query("timezone") timezone: String = "auto"
    ): WeatherDTO

}