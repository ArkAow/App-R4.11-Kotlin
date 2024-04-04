package fr.unilim.weatherapplication.android

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import view.FavoritesManagerPage

@Composable
fun FavoritesManagerScreen() {
    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize()
    ) {
        val favoritesManagerPage = FavoritesManagerPage()
        favoritesManagerPage.FavoritesManagement()
    }
}