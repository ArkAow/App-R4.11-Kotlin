package view

import android.content.Context
import android.content.Intent
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat.startActivity
import fr.unilim.weatherapplication.android.FavoritesManagerActivity
import fr.unilim.weatherapplication.android.MainActivity
import fr.unilim.weatherapplication.android.MyApplicationTheme
import fr.unilim.weatherapplication.android.R
import model.City
import java.util.Calendar

class WeatherAppHomePage {

    @Composable
    fun WeatherApp(contextProvided: Context) {
        // liste de villes
        val favoritesCities = listOf(
            City("Paris", "75", "Sunny", 25, 25, 21),
            City("Limoges", "87", "rainy", 15, 25, 21),
            City("Poitiers", "86", "sloudy", 20, 25, 21),
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

                            // Bouton "Modifier les favoris"
                            Button(
                                onClick = {
                                    val modifyFavorite = Intent(contextProvided, FavoritesManagerActivity::class.java)
                                    contextProvided.startActivity(modifyFavorite)
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
                    FavoriteCityItem(
                        city = cities[index]
                    )
                }
            }
        }
    }

    @Composable
    fun SearchCitySection() {
        var searchText by remember { mutableStateOf("") }

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
                value = TextFieldValue(text = searchText),
                onValueChange = { searchText = it.text },
                label = {
                    Text(
                        text = "Saisissez le nom d'une ville",
                        color = Color.White
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )
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
}