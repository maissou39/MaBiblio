package com.example.bibliogest.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter

@Entity(tableName = "books")
data class Book(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    // Champs obligatoires
    val title: String,
    val authorId: Int, // Relation avec Author.id
    val publicationYear: Int? = null,
    // Champ facultatif
    val description: String? = null,
    // État du livre (Neuf ou Ancien)
    val state: BookState = BookState.INCONNU,

    //ajout du favoris
    val isFavorite: Boolean = false
)

enum class BookState {
    NEUF, ANCIEN, INCONNU
}

@TypeConverter
fun toBookState(stateName: String): BookState {
    return BookState.valueOf(stateName)
}

@TypeConverter
fun fromBookState(state: BookState): String {
    return state.name
}
