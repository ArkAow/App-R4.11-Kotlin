package fr.unilim.weatherapplication.android

import Data.WeatherResponse
import Data.WeatherViewModel
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.InternalCoroutinesApi
import model.City
import java.util.Calendar
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {

    @InternalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    WeatherApp()
                }
            }
        }
    }
}

@Composable
fun WeatherApp() {

    // liste de villes
    val favoritesCities = listOf(
        City("", "75", "Sunny", 25, 25, 21),
        City("Limoges", "87", "rainy", 15, 25, 21),
        City("Poitiers", "86", "cloudy", 20, 25, 21),
        City("Fleuré", "86", "sunny", 28, 28, 21),
        City("Nieuil", "16", "stormy", 22, 25, 21)
    )

    val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    val backgroundImages = listOf(
        R.drawable.night_image,
        R.drawable.morning_image,
        R.drawable.afternoom_image,
        R.drawable.evening_image
    )
    val backgroundImage = backgroundImages[currentHour / 6]

    Box(modifier = Modifier.fillMaxSize()) {
        // Couche pour l'image de fond
        Image(
            painter = painterResource(id = backgroundImage),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            // Couche pour le fond noir
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.6f))
                    .padding(16.dp)
            ) {

                // Couche pour le contenu (texte et autres éléments)
                Box(modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.TopStart
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {

                        // Ajout de l'icône et du nom de l'application
                        AppHeader()

                        // Espace entre les sections
                        Spacer(modifier = Modifier.height(48.dp))

                        // Section des favoris
                        FavoriteCitiesSection(favoritesCities)

                        val context = LocalContext.current
                        val intent = Intent(context, FavoritesManagerActivity::class.java)

                        // Bouton "Modifier les favoris" qui envoi vers une autre activité
                        Button(
                            onClick = {
                                intent.putExtra("city1", favoritesCities[0].name)
                                intent.putExtra("city2", favoritesCities[1].name)
                                intent.putExtra("city3", favoritesCities[2].name)
                                intent.putExtra("city4", favoritesCities[3].name)
                                intent.putExtra("city5", favoritesCities[4].name)

                                context.startActivity(intent)
                            },
                            colors = ButtonDefaults.buttonColors(
                                Color(0xFF7eb4b2)
                            ),
                            modifier = Modifier
                                .padding(
                                    top = 16.dp,
                                    end = 32.dp,
                                    start = 32.dp
                                )
                                .fillMaxWidth()
                        ) {
                            Text("Modifier les favoris")
                        }

                        // Espace entre les sections
                        Spacer(modifier = Modifier.height(48.dp))

                        // Section de recherche
                        SearchCitySection()
                    }
                }
            }
        }
    }
}

@Composable
fun AppHeader() {
    Row(
        verticalAlignment = Alignment.CenterVertically

    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .background(
                    color = Color.Black.copy(alpha = 0.6f),
                    shape = RoundedCornerShape(16.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.weather_report_icon),
                    contentDescription = "Weather Icon",
                    contentScale = ContentScale.FillHeight,
                    modifier = Modifier.size(64.dp)
                )
                // Nom de l'application à côté de l'icône
                Text(
                    text = "Weather Application",
                    color = Color.White,
                    modifier = Modifier.padding(start = 24.dp)
                )
            }
        }
    }
}

@Composable
fun FavoriteCitiesSection(cities: List<City>) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Favoris",
            color = Color.White,
            modifier = Modifier.padding(16.dp)
        )
        LazyRow {
            items(cities.size) { index ->
                val city = cities[index]
                if (city.name.isNotEmpty()) { // Vérifier si le nom de la ville n'est pas vide
                    FavoriteCityItem(city = city)
                }
            }
        }
    }
}

@Composable
fun SearchCitySection(weatherViewModel: WeatherViewModel = viewModel()) {
    val weatherResponse by weatherViewModel.weatherState.observeAsState()
    var searchedName by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Rechercher",
            color = Color.White,
            modifier = Modifier
                .padding(vertical = 16.dp)
                .padding(horizontal = 8.dp)

        )
        OutlinedTextField(
            value = searchedName,
            onValueChange = { searchedName = it },
            label = {
                Text(
                    text = "Saisissez le nom d'une ville",
                    color = Color.White
                )
            },
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(color = Color.White)
        )

        Button(
            onClick = {
                weatherViewModel.getWeatherForCity(searchedName)
            },
            colors = ButtonDefaults.buttonColors(
                Color(0xFF7eb4b2)
            ),
            modifier = Modifier
                .padding(
                    top = 16.dp,
                    end = 32.dp,
                    start = 32.dp
                )
                .fillMaxWidth()
        ) {
            Text(
                text = "Valider",
            )
        }

        weatherResponse?.let { weather ->
            WeatherDisplay(weather = weather)
        }
    }
}


@Composable
fun FavoriteCityItem(city: City) {
    val weatherImage: Painter = painterResource(id = getWeatherImageResourceId(city.meteo))

    Box(
        modifier = Modifier
            .size(120.dp, 200.dp)
            .padding(end = 10.dp)
    ) {
        Image(
            painter = weatherImage,
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(16.dp)),
            contentScale = ContentScale.FillHeight
        )
        Box(
            modifier = Modifier
                .size(120.dp, 200.dp)
                .background(
                    Color.Black.copy(
                        alpha = 0.4f),
                    shape = RoundedCornerShape(16.dp)
                ),
            contentAlignment = Alignment.TopCenter
        ) {
            Text(
                text = city.name,
                color = Color.White,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}

@Composable
fun getWeatherImageResourceId(weather: String): Int {
    return when (weather.lowercase()) {
        "sunny" -> R.drawable.sunny_weather
        "rainy" -> R.drawable.rainy_weather
        "cloudy" -> R.drawable.cloudy_weather
        "stormy" -> R.drawable.stormy_weather
        else -> R.drawable.cloudy_weather
    }
}


@Composable
fun WeatherDisplay(weather: WeatherResponse) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "${weather.name}",
            color = Color(0xFF58AAEB),
            fontSize = 52.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = androidx.compose.ui.text.font.FontFamily.Cursive
        )

        weather.weather.forEach { weatherDetail ->
            val weatherText = when (weatherDetail.main) {
                "Clouds" -> "Nuageux"
                "Clear" -> "Ensoleillé"
                "Rain" -> "Pluie"
                else -> "Inconnu"
            }

            Text(
                text = "${weather.main.temp}°C",
                color = Color(0xFF58AAEB),
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = androidx.compose.ui.text.font.FontFamily.Cursive
            )
            Spacer(modifier = Modifier.height(16.dp))

            var temp = weather.main.temp

            if (temp <= 5) {
                //mettre image cold

            } else if (temp > 5 && temp <= 15) {
                //mettre image normal_temp

            } else if (temp > 15) {
                //mettre image hot

            }

            Spacer(modifier = Modifier.height(16.dp))

            weather.weather.forEach { weatherDetail ->

                Text(
                    text = "$weatherText",
                    style = TextStyle(color = Color(0xFF58AAEB), fontSize = 26.sp),
                    fontWeight = FontWeight.Bold,
                    fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace
                )
                Spacer(modifier = Modifier.height(16.dp))

                if (weatherDetail.main == "Clouds") {
                    //mettre image clouds

                } else if (weatherDetail.main == "Clear") {
                    //mettre image clear

                } else if (weatherDetail.main == "Rain") {
                    //mettre image rain

                } else if (weatherDetail.main == "Thunderstorm") {
                    //mettre image storm
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Spacer(modifier = Modifier.height(50.dp))
                    //mettre image cloudy
                    Text(
                        text = "${weather.wind.speed}m/s",
                        color = Color(0xFF58AAEB),
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = androidx.compose.ui.text.font.FontFamily.Cursive
                    )
                    Spacer(modifier = Modifier.height(30.dp))
                }

                Spacer(modifier =Modifier.height(32.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Min : ${weather.main.temp_min}°C",
                        color = Color(0xFF58AAEB),
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = androidx.compose.ui.text.font.FontFamily.Cursive
                    )

                    Text(
                        text = "Max : ${weather.main.temp_max}°C",
                        color = Color(0xFF58AAEB),
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = androidx.compose.ui.text.font.FontFamily.Cursive
                    )

                    Spacer(modifier =Modifier.height(16.dp))
                }
            }
            Spacer(modifier =Modifier.height(85.dp))
            Text(text = "Application développé par Baptiste Lafarge et Axel Pignol", color = Color.Black, fontSize = 12.sp)
        }
    }
}
