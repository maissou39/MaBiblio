package com.example.bibliogest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import com.example.bibliogest.navigation.NavGraph
import com.example.bibliogest.ui.theme.BiblioGestTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BiblioGestTheme {
                BiblioGestApp()
            }
        }
    }
}


@Composable
fun BiblioGestApp() {
    NavGraph()
}
