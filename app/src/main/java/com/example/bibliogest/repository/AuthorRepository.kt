package com.example.bibliogest.repository

import com.example.bibliogest.database.Author
import com.example.bibliogest.database.AuthorDAO
import kotlinx.coroutines.flow.Flow

class AuthorRepository(private val auteurDao: AuthorDAO) {

    fun getAllAuthors(): Flow<List<Author>> {
        return auteurDao.getAllAuthors()
    }

    suspend fun getAuthorById(id: Int): Author? {
        return auteurDao.getAuthorById(id)
    }

    suspend fun insertAuthor(author: Author) {
        auteurDao.insertAuthor(author)
    }

    suspend fun deleteAuthor(author: Author) {
        auteurDao.deleteAuthor(author)
    }
}