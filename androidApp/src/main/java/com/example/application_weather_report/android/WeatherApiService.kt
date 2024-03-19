import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("your-endpoint-here")
    suspend fun getWeatherData(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("apikey") apiKey: String
    ): WeatherResponse
}

val retrofit = Retrofit.Builder()
    .baseUrl("https://api.meteomatics.com/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val service = retrofit.create(WeatherApiService::class.java)

