package Data

import retrofit2.Response

class WeatherRepository {

    private val retrofitService = RetrofitInstance.api

    suspend fun getWeather(city: String, apiKey: String): Response<WeatherResponse> {
        return retrofitService.getWeather(city, apiKey)
    }
}
