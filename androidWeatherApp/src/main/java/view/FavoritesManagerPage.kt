package view

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class FavoritesManagerPage {

    @Composable
    fun FavoritesManagement() {

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Gestion des favoris")

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = { /* Logique pour ajouter un favori */ }) {
                Text("Ajouter un favori")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = { /* Logique pour supprimer un favori */ }) {
                Text("Supprimer un favori")
            }
        }
    }
}