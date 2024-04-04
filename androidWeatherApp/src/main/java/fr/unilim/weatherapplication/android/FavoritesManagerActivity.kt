package fr.unilim.weatherapplication.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class FavoritesManagerActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting()
                }
            }
        }
    }
}



@Composable
fun Greeting() {
    var text by remember { mutableStateOf("") }
    var birthDate by remember { mutableStateOf("") }

    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(
            text = "Bienvenue",
            fontWeight = FontWeight.Bold,
            fontSize = 40.sp
        )
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier
                .padding(10.dp,150.dp)
        ){

            Text(
                text =
                if (birthDate == ""){
                    text
                }
                else if(text == ""){
                    "vous etes né(e) le $birthDate"
                } else {
                    "$text, né(e) le $birthDate"
                }
            )

            TextField(
                value = text,
                onValueChange = { text = it },
                label = {
                    Text(
                        text = "Saisir votre nom"
                    )
                },
                modifier = Modifier
                    .padding(0.dp, 20.dp)
            )

            TextField(
                value = birthDate,
                onValueChange = { birthDate = it },
                label = {
                    Text(
                        text = "Saisir votre date de naissance (jj/mm/aaaa)",
                        fontSize = 12.sp
                    )
                },
                modifier = Modifier
                    .padding(0.dp, 20.dp)
            )

            Button(
                onClick = {

                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Blue,
                )
            ) {
                Text(
                    text = "Valider",
                )
            }
        }
    }
}