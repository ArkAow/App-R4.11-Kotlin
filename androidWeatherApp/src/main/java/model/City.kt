package model

data class City(
    val name: String,
    val departement: String,
    val meteo: String,
    val temp: Int,
    val temp_max: Int,
    val temp_min: Int
)