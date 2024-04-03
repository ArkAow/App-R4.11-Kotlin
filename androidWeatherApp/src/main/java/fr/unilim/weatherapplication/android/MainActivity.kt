package fr.unilim.weatherapplication.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import view.WeatherAppHomePage

class MainActivity : ComponentActivity() {

    val contextProvided = this.applicationContext

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val weatherAppHomePage = WeatherAppHomePage()
                    weatherAppHomePage.WeatherApp(contextProvided)
                }
            }
        }
    }
}