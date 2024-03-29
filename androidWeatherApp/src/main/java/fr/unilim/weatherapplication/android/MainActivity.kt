package fr.unilim.weatherapplication.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text

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
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
    ) {
        FavoriteCitiesSection(
            cities = listOf("Paris", "Limoges", "Poitiers"),
            onCityClicked = { }
        )
        Divider(modifier = Modifier.height(1.dp), color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f))
        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
            SearchCitySection()
        }
    }
}

@Composable
fun FavoriteCitiesSection(cities: List<String>, onCityClicked: (String) -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Favoris",
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
            modifier = Modifier
                .padding(vertical = 16.dp)
                .padding(horizontal = 8.dp)
        )
        OutlinedTextField(
            value = TextFieldValue(text = searchText),
            onValueChange = { searchText = it.text },
            label = { Text("Saisissez le nom d'une ville") },
            modifier = Modifier.fillMaxWidth()
        )
    }
}


@Composable
fun FavoriteCityItem(cityName: String, onCityClicked: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { onCityClicked(cityName) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = cityName)
    }
}

@Preview
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        WeatherApp()
    }
}