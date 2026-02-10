package com.example.bibliogest.repository

import com.example.bibliogest.database.Book
import com.example.bibliogest.database.BookDao
import kotlinx.coroutines.flow.Flow

class BookRepository(private val bookDao: BookDao) {

    fun getAllBooks(): Flow<List<Book>> {
        return bookDao.getAllBooks()
    }

    suspend fun getBookById(id: Int): Book? {
        return bookDao.getBookById(id)
    }

    suspend fun insertBook(book: Book): Long {
        return bookDao.insertBook(book)
    }

    suspend fun updateBook(book: Book) {
        bookDao.updateBook(book)
    }

    suspend fun deleteBook(book: Book) {
        bookDao.deleteBook(book)
    }

    suspend fun toggleFavorite(bookId: Int, isFavorite: Boolean) {
        bookDao.updateFavoriteStatus(bookId, isFavorite)
    }

    fun getFavoriteBooks(): Flow<List<Book>> {
        return bookDao.getFavoriteBooks()
    }
}