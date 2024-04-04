package fr.unilim.weatherapplication.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp

class FavoritesManagerActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Favorites()
                }
            }
        }
    }
}

@Composable
fun Favorites() {

    Column(modifier = Modifier.fillMaxWidth()) {

        // Titre "Favoris"
        Text(
            text = "Favoris",
            color = Color.Black,
            modifier = Modifier.padding(16.dp)
        )

        // Liste des villes
        repeat(5) {
            val cityName = "Ville ${it + 1}"
            Text(
                text = cityName,
                color = Color.Black,
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .clickable {
                        // Action lorsqu'une ville est sélectionnée
                        // Ici, vous pouvez implémenter la logique pour supprimer la ville
                    }
            )
        }

        // Bouton "Supprimer"
        Button(
            onClick = {
                // Action lorsque le bouton "Supprimer" est cliqué
            },
            colors = ButtonDefaults.buttonColors(
                Color(0xFF7eb4b2)),
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text("Supprimer")
        }

        // Bouton "Ajouter"
        IconButton(
            onClick = {
                // Action lorsque le bouton "Ajouter" est cliqué
            },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.plus_icon),
                contentDescription = "Ajouter"
            )
        }

        // Bouton "Retour" avec une icône de croix
        IconButton(
            onClick = {
                // Action lorsque le bouton "Retour" est cliqué
            },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.cross_icon),
                contentDescription = "Retour"
            )
        }
    }
}
