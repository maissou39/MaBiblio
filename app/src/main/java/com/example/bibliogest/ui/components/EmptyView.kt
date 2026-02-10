package com.example.bibliogest.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun EmptyView( //parametre de la fonction predefini
    paddingValues: PaddingValues,
    text: String,
    onButtonClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        contentAlignment = Alignment.Center
    ) {
        Card(modifier = Modifier.padding(16.dp)) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = text,
                    textAlign = TextAlign.Center
                )
                Button(onClick = onButtonClick) {
                    Text(text = "Ajouter un livre")
                }
            }
        }
    }
}

//@Composable
//fun EmptyView(paddingValues: PaddingValues, onAddClick: () -> Unit) {
//    // Afficher un message si la liste est vide
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(paddingValues),
//        contentAlignment = Alignment.Center
//    ) {
//        Card(modifier = Modifier.padding(16.dp)) {
//            Column(
//                modifier = Modifier.padding(16.dp),
//                horizontalAlignment = Alignment.CenterHorizontally,
//                verticalArrangement = Arrangement.spacedBy(8.dp)
//            ) {
//                Text(
//                    text = "Aucun livre dans la bibliothèque.",
//                    textAlign = TextAlign.Center
//                )
//                Button(onClick = onAddClick) {
//                    Text(text = "Ajouter un livre")
//                }
//            }
//        }
//    }
//}
