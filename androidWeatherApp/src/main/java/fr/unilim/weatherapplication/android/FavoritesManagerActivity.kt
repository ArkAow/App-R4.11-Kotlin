package fr.unilim.weatherapplication.android

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import model.City
import java.util.Calendar

class FavoritesManagerActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    // on récupere les noms des villes
                    val city1 = intent.getStringExtra("city1") ?: ""
                    val city2 = intent.getStringExtra("city2") ?: ""
                    val city3 = intent.getStringExtra("city3") ?: ""
                    val city4 = intent.getStringExtra("city4") ?: ""
                    val city5 = intent.getStringExtra("city5") ?: ""

                    // on les met dans une liste en excluant ceux qui ont un nom vide
                    val cities = listOf(city1, city2, city3, city4, city5).filter { it.isNotBlank() }

                    Favorites(cities)
                }
            }
        }
    }
}

@Composable
fun Favorites(cities: List<String>) {

    val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    val backgroundImages = listOf(
        R.drawable.night_image,
        R.drawable.morning_image,
        R.drawable.afternoom_image,
        R.drawable.evening_image
    )
    val backgroundImage = backgroundImages[currentHour / 6]

    // Liste mutable des villes
    val citiesState = remember { mutableStateListOf(*cities.toTypedArray()) }

    // Valeur du texte saisi dans le TextField
    var cityNameInput by remember { mutableStateOf("") }

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
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.TopStart
                ) {

                    Column(modifier = Modifier.padding(16.dp)) {

                        // Titre "Favoris"
                        Text(
                            text = "Favoris",
                            color = Color.White,
                            fontSize = 32.sp,
                            modifier = Modifier.padding(16.dp)
                        )

                        // Liste des villes avec bouton "Supprimer"
                        citiesState.forEachIndexed { index, cityName ->
                            if (cityName.isNotEmpty()) { // Vérifier si cityName n'est pas vide
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    // Nom de la ville
                                    Box(
                                        modifier = Modifier
                                            .weight(1f)
                                            .padding(horizontal = 16.dp, vertical = 8.dp)
                                            .background(Color(0xFF3B3B3B), MaterialTheme.shapes.medium)
                                    ) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Text(
                                                text = cityName,
                                                color = Color.White,
                                                modifier = Modifier.padding(16.dp)
                                            )

                                            Row(
                                                horizontalArrangement = Arrangement.End,
                                                modifier = Modifier
                                                    .padding(horizontal = 16.dp, vertical = 8.dp)
                                                    .fillMaxWidth()
                                            )
                                            {

                                                // Bouton "Supprimer"
                                                Button(
                                                    onClick = {
                                                        // Supprimer la ville de la liste
                                                        citiesState.removeAt(index)
                                                    },
                                                    modifier = Modifier.padding(horizontal = 8.dp),
                                                ) {
                                                    Text("X", fontSize = 12.sp)
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        // Espace entre les éléments
                        Spacer(modifier = Modifier.height(16.dp))

                        // TextField pour entrer le nom de la ville à ajouter
                        TextField(
                            value = cityNameInput,
                            enabled = citiesState.size < 5,
                            onValueChange = { cityNameInput = it },
                            label = { Text("Nom de la ville") },
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .fillMaxWidth()
                        )

                        // Bouton "Ajouter"
                        Button(
                            onClick = {
                                // Ajouter le nom de la ville à la liste si la saisie n'est pas vide et n'est pas égale à ""
                                if (cityNameInput.isNotBlank() && cityNameInput != "" && citiesState.size < 5) {
                                    citiesState.add(cityNameInput)
                                    // Réinitialiser le champ de saisie
                                    cityNameInput = ""
                                }
                            },
                            enabled = citiesState.size < 5, // Rendre le bouton actif si le nombre de villes favorites est inférieur à 5
                            modifier = Modifier
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                                .fillMaxWidth()
                        ) {
                            Text("Ajouter")
                        }

                        val context = LocalContext.current
                        val intent = Intent(context, MainActivity::class.java)

                        // Bouton "Retour"
                        Button(
                            onClick = {
                                intent.putStringArrayListExtra("favoriteCities", ArrayList(cities))
                                context.startActivity(intent)
                            },
                            colors = ButtonDefaults.buttonColors(
                                Color(0xFF7eb4b2)
                            ),
                            modifier = Modifier
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                                .fillMaxWidth()
                        ) {
                            Text("Retour")
                        }
                    }
                }
            }
        }
    }
}
