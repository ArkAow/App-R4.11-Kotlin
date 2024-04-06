package Data

class WeatherRepository {

    private val retrofitService = RetrofitInstance.api

    suspend fun getWeather(city: String, apiKey: String): WeatherResponse? {
        val response = retrofitService.getWeather(city, apiKey)
        return if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }
}
