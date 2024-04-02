package fr.unilim.weatherapplication.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.util.Calendar

class MainActivity : ComponentActivity() {
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
    val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    val backgroundImages = listOf(
        R.drawable.morning_image,
        R.drawable.afternoom_image,
        R.drawable.evening_image,
        R.drawable.night_image
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
                .padding(24.dp,24.dp),
            contentAlignment = Alignment.Center
        ) {
            // Couche pour le fond noir
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.4f))
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
                        FavoriteCitiesSection(
                            cities = listOf("Paris", "Limoges", "Poitiers", "Fleuré", "Nieuil"),
                            onCityClicked = { }
                        )

                        // Bouton "Modifier les favoris"
                        Button(
                            onClick = { /* Logique à ajouter */ },
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
                    color = Color.Black.copy(alpha = 0.4f),
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
fun FavoriteCitiesSection(cities: List<String>, onCityClicked: (String) -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Favoris",
            color = Color.White,
            modifier = Modifier.padding(16.dp)
        )
        LazyRow {
            items(cities.size) { index ->
                FavoriteCityItem(
                    cityName = cities[index],
                    onCityClicked = onCityClicked
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
                )},
            modifier = Modifier.fillMaxWidth()
        )
    }
}


@Composable
fun FavoriteCityItem(cityName: String, onCityClicked: (String) -> Unit) {
    Box(
        modifier = Modifier
            .size(120.dp, 200.dp)
            .padding(4.dp)
            .clickable { onCityClicked(cityName) }
            .background(
                color = Color.Black.copy(alpha = 0.4f),
                shape = RoundedCornerShape(16.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = cityName,
            color = Color.White,
            modifier = Modifier
                .padding(bottom = 64.dp)
        )
    }
}

@Preview
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        WeatherApp()
    }
}
