package com.example.bibliogest.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

//fichier qui permet de faire les requetes sql
@Dao
interface BookDao {
    // Récupérer tous les livres, avec Flow pour les mises à jour en temps réel
    @Query("SELECT * FROM books ORDER BY title ASC")
    fun getAllBooks(): Flow<List<Book>>

    // Récupérer un livre par son ID
    @Query("SELECT * FROM books WHERE id = :bookId")
    suspend fun getBookById(bookId: Int): Book?

    // Insérer un nouveau livre
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBook(book: Book): Long

    // Mettre à jour un livre existant
    @Update
    suspend fun updateBook(book: Book)

    // Supprimer un livre
    @Delete
    suspend fun deleteBook(book: Book)

    // methode pour mettre a jour le favoris
    @Query("UPDATE books SET isFavorite = :isFavorite WHERE id = :bookId")
    suspend fun updateFavoriteStatus(bookId: Int, isFavorite: Boolean)

    //methode pour recuperer les livres favoris
    @Query("SELECT * FROM books WHERE isFavorite = 1")
    fun getFavoriteBooks(): Flow<List<Book>>
}