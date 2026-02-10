@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.bibliogest.ui.bookdetailscreen

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.bibliogest.database.Book
import com.example.bibliogest.database.Author
import com.example.bibliogest.database.fromBookState
import com.example.bibliogest.model.BookViewModel
import com.example.bibliogest.ui.components.LoadingView
import kotlinx.coroutines.launch

@Composable
fun BookDetailScreen(
    bookId: Int, viewModel: BookViewModel, onBackClick: () -> Unit, onEditClick: (Int) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val authors by viewModel.authors.collectAsState()

    var book by remember { mutableStateOf<Book?>(null) }
    var author by remember { mutableStateOf<Author?>(null) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    // Chargement des détails du livre et de l'auteur
    LaunchedEffect(bookId, authors) {
        book = viewModel.getBookById(bookId)
        book?.let { currentBook ->
            author = authors.find { it.id == currentBook.authorId }
        }
    }

    Scaffold(topBar = {
        TopAppBar(title = { Text("Détails du livre") }, colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
        ), navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Retour")
            }
        }, actions = {
            IconButton(onClick = { book?.let { onEditClick(it.id) } }) {
                Icon(Icons.Default.Edit, contentDescription = "Modifier")
            }
            IconButton(onClick = { showDeleteDialog = true }) {
                Icon(Icons.Default.Delete, contentDescription = "Supprimer")
            }
        })
    }) { paddingValues ->
        if (showDeleteDialog) {
            AlertDialog(onDismissRequest = { showDeleteDialog = false },
                title = { Text("Confirmation") },
                text = { Text("Voulez-vous vraiment supprimer ce livre ?") },
                confirmButton = {
                    Button(onClick = {
                        coroutineScope.launch {
                            book?.let { viewModel.deleteBook(it) }
                            onBackClick()
                        }
                        showDeleteDialog = false
                    }) { Text("Supprimer") }
                },
                dismissButton = {
                    Button(onClick = { showDeleteDialog = false }) { Text("Annuler") }
                })
        }
        
        book?.let { currentBook ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                Text(
                    text = currentBook.title,
                    style = MaterialTheme.typography.displaySmall,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Auteur: ", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyLarge)
                    Text(text = author?.let { "${it.prenom} ${it.nom}" } ?: "Inconnu")
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Année: ", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyLarge)
                    Text(text = currentBook.publicationYear?.toString() ?: "Non spécifié")
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "État: ", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyLarge)
                    Text(text = fromBookState(currentBook.state))
                }

                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Description: ", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = currentBook.description ?: "Aucune description disponible",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        } ?: LoadingView()
    }
}
