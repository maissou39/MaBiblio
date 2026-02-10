package com.example.bibliogest.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface AuthorDAO {
    @Query("SELECT * FROM authors ORDER BY nom ASC")
    fun getAllAuthors(): Flow<List<Author>>

    @Query("SELECT * FROM authors WHERE id = :id")
    suspend fun getAuthorById(id: Int): Author?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAuthor(author: Author)

    @Update
    suspend fun updateAuthor(author: Author)

    @Delete
    suspend fun deleteAuthor(author: Author)
}
