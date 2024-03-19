data class WeatherResponse(
    val weather: WeatherData,
    val temperature: TemperatureData,
    val wind: WindData,
    val uvIndex: Int
)

data class WeatherData(
    val type: String // Ensoleillé, Pluvieux, Nuageux, etc.
)

data class TemperatureData(
    val current: Double,
    val min: Double,
    val max: Double
)

data class WindData(
    val speed: Double
)
