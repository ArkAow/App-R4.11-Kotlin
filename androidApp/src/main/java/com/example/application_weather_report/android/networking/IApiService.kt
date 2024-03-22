package com.example.application_weather_report.android.networking

import com.example.application_weather_report.android.model.WeatherResponse
import retrofit2.Call
import retrofit2.http.*


interface IApiService {

    // Get current weather data
    @GET("current.json")
    fun getCurrentWeather(
        @Query("key") key: String = ApiConfig.API_KEY,
        @Query("q") city: String,
        @Query("aqi") aqi: String = "no"
    ): Call<WeatherResponse>
}