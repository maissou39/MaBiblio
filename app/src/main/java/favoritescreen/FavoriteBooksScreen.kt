package com.example.bibliogest.ui.favoritescreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.bibliogest.model.BookViewModel
import com.example.bibliogest.ui.components.BookItem
import com.example.bibliogest.ui.components.EmptyView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteBooksScreen(
    viewModel: BookViewModel,
    onBookClick: (Int) -> Unit,
    onBackClick: () -> Unit
) {
    // On observe uniquement la liste des favoris
    val favoriteBooks by viewModel.favoriteBooks.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mes Favoris ❤️") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Retour")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->
        if (favoriteBooks.isEmpty()) {
            EmptyView(
                paddingValues = paddingValues,
                text = "Vous n'avez pas encore de favoris.",
                onButtonClick = onBackClick // Ou une autre action
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                items(favoriteBooks) { book ->
                    BookItem(
                        book = book,
                        onItemClick = { onBookClick(book.id) },
                        onFavoriteClick = { viewModel.toggleFavorite(book) }
                    )
                    Divider()
                }
            }
        }
    }
}