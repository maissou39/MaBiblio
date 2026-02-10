package com.example.bibliogest.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bibliogest.database.Book
import com.example.bibliogest.database.BookState

@Composable
fun BookItem(
    book: Book,
    onItemClick: () -> Unit,
    onFavoriteClick: () -> Unit = {}
) {
    val stateColor = when (book.state) {
        BookState.NEUF -> Color(0xFFB26785)
        BookState.ANCIEN -> Color(0xFF5E1437)
        else -> Color.Gray
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onItemClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium,
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Card(
                        modifier = Modifier.weight(1f),
                        colors = CardDefaults.cardColors(
                            containerColor = stateColor.copy(alpha = 0.1f)
                        ),
                        shape = MaterialTheme.shapes.small
                    ) {
                        Text(
                            text = "  📖 " + book.title,
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(8.dp),
                            fontWeight = FontWeight.Bold,
                            color = Color.DarkGray
                        )
                    }

                    IconButton(
                        onClick = onFavoriteClick,
                        modifier = Modifier.padding(start = 8.dp)
                    ) {
                        Icon(
                            imageVector = if (book.isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                            contentDescription = if (book.isFavorite) "Retirer des favoris" else "Ajouter aux favoris",
                            tint = if (book.isFavorite) Color.Red else Color.Gray,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }

                // Ligne Auteur (On affiche l'ID pour l'instant dans l'item)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Face,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp),
                        tint = Color.Gray
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "ID Auteur: ${book.authorId}",
                        style = MaterialTheme.typography.titleSmall,
                        fontStyle = FontStyle.Italic,
                        fontWeight = FontWeight.Light
                    )
                }

                // Ligne Date
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp),
                        tint = Color.Gray
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Année : ${book.publicationYear ?: "Non spécifié"}",
                        style = MaterialTheme.typography.titleSmall
                    )
                }

                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = stateColor.copy(alpha = 0.1f)
                    ),
                    shape = MaterialTheme.shapes.small
                ) {
                    Text(
                        text = "État: ${book.state}",
                        style = MaterialTheme.typography.labelLarge,
                        color = stateColor,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        fontWeight = FontWeight.Bold
                    )
                }

                if (!book.description.isNullOrBlank()) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = MaterialTheme.shapes.medium,
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = " 📜 Description",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF876D58),
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            Text(
                                text = book.description ?: "",
                                style = MaterialTheme.typography.bodyMedium,
                                fontStyle = FontStyle.Italic,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                lineHeight = 20.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BookItemPreview() {
    MaterialTheme {
        Column {
            BookItem(
                book = Book(
                    id = 1,
                    title = "Le Petit Prince",
                    authorId = 1,
                    publicationYear = 1943,
                    description = "Un classique français",
                    state = BookState.NEUF,
                    isFavorite = true
                ),
                onItemClick = {}
            )
        }
    }
}
