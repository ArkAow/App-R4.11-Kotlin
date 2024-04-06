package fr.unilim.weatherapplication.android

import Data.WeatherResponse
import Data.WeatherViewModel
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.InternalCoroutinesApi
import java.util.Calendar

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
        "Bordeaux", "Limoges", "Poitiers", "Fleuré", "Paris"
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
                    .background(
                        Color.Black.copy(alpha = 0.6f),
                        shape = RoundedCornerShape(16.dp)
                    )
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
                                intent.putExtra("city1", favoritesCities[0])
                                intent.putExtra("city2", favoritesCities[1])
                                intent.putExtra("city3", favoritesCities[2])
                                intent.putExtra("city4", favoritesCities[3])
                                intent.putExtra("city5", favoritesCities[4])

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
fun FavoriteCitiesSection(cities: List<String>, weatherViewModel: WeatherViewModel = viewModel()) {

    Column(modifier = Modifier.fillMaxWidth()) {
        val weatherResponse by weatherViewModel.weatherState.observeAsState()

        Text(
            text = "Favoris",
            color = Color.White,
            modifier = Modifier.padding(16.dp)
        )
        LazyRow {
            items(cities.size) { index ->
                val city = cities[index]
                if (city.isNotBlank()) {
                    println("Le nom de la ville n'est pas vide")
                    weatherResponse?.let { weather ->
                        println("La réponse de l'API est OK")
                        FavoriteCityItem(weather = weather)
                    } ?: Text(
                        "Erreur lors de \nla récupération \ndes données",
                        color = Color.LightGray,
                        modifier = Modifier.padding(start = 16.dp))
                }
            }
        }
    }
}

@Composable
fun SearchCitySection(weatherViewModel: WeatherViewModel = viewModel()) {
    val weatherResponse by weatherViewModel.weatherState.observeAsState()
    var searchedName by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) } // Etat pour contrôler la visibilité du dialogue

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Recherche",
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
                showDialog = true // Mettre à vrai pour afficher le dialogue
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

        // Afficher le dialogue conditionnellement
        if (showDialog) {
            weatherResponse?.let { weather ->
                ShowWeather(weatherResponse = weather)
                showDialog = false

            } ?: run {
                ShowWeather(weatherResponse = null)
                showDialog = false
            }
        }
    }
}


@Composable
fun FavoriteCityItem(weather: WeatherResponse) {
    val weatherImage: Painter = painterResource(id = getWeatherImageResourceId(weather.weather[0].main))

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
                        alpha = 0.4f
                    ),
                    shape = RoundedCornerShape(16.dp)
                ),
            contentAlignment = Alignment.TopCenter
        ) {
            Column {
                // nom de la ville
                Text(
                    text = "${weather.name}°C",
                    color = Color.White,
                    modifier = Modifier.padding(16.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // temperature de la ville
                Text(
                    text = "${weather.main.temp}°C",
                    color = Color.White,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}

@Composable
fun getWeatherImageResourceId(weather: String): Int {
    return when (weather.lowercase()) {
        "Clear" -> R.drawable.sunny_weather
        "Rain" -> R.drawable.rainy_weather
        "Clouds" -> R.drawable.cloudy_weather
        "Thunderstorm" -> R.drawable.stormy_weather
        else -> R.drawable.cloudy_weather
    }
}


@Composable
fun ShowWeather(weatherResponse: WeatherResponse?) {
    val builder = AlertDialog.Builder(LocalContext.current)
    builder.setTitle("Résultat de la recherche")

    // Si les données météorologiques sont disponibles, affichez-les dans le dialogue
    val message = "Nom de la ville : ${weatherResponse?.name}\n" +
            "Type de temps : ${weatherResponse?.weather?.getOrNull(0)?.main}\n" +
            "Température : ${weatherResponse?.main?.temp}°C\n" +
            "Température minimale : ${weatherResponse?.main?.temp_min}°C\n" +
            "Température maximale : ${weatherResponse?.main?.temp_max}°C\n" +
            "Vitesse du vent : ${weatherResponse?.wind?.speed} m/s\n"

    // Ajoutez d'autres informations météorologiques selon vos besoins
    builder.setMessage(message)

    // Ajoutez un bouton "OK" pour fermer le dialogue
    builder.setPositiveButton("OK") { dialog, _ ->
        dialog.dismiss()
    }

    // Affichez le dialogue
    builder.show()
}