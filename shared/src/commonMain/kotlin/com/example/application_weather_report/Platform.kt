package com.example.application_weather_report

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform