package com.example.bibliogest.ui.booklistscreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.bibliogest.model.BookViewModel
import com.example.bibliogest.ui.components.BookItem
import com.example.bibliogest.ui.components.EmptyView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookListScreen(
    viewModel: BookViewModel, onBookClick: (Int) -> Unit, onAddClick: () -> Unit
) {
    val books by viewModel.books.collectAsState()

    Scaffold(topBar = {
        TopAppBar(
            title = { Text("Ma Bibliothèque") },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
                titleContentColor = MaterialTheme.colorScheme.onPrimary,
            ),
        )
    }, floatingActionButton = {
        FloatingActionButton(
            onClick = onAddClick
        ) {
            Icon(Icons.Filled.Add, contentDescription = "Ajouter un livre")
        }
    }) { paddingValues ->
        if (books.isEmpty()) { //si la liste est vide
            EmptyView(
                text = "\uD83D\uDCDA\nVotre bibliothèque est vide",
                onButtonClick = onAddClick,
                paddingValues = paddingValues,
            )
        } else {
            // Afficher la liste des livres
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                items(books) { book ->
                    BookItem(
                        book = book,
                        onItemClick = { onBookClick(book.id) },
                        onFavoriteClick = { 
                            viewModel.toggleFavorite(book) 
                        }
                    )
                    Divider()
                }
            }
        }
    }
}
