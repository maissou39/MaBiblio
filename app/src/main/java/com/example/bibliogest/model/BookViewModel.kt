package com.example.bibliogest.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.bibliogest.database.Author
import com.example.bibliogest.database.Book
import com.example.bibliogest.database.BookDatabase
import com.example.bibliogest.repository.AuthorRepository
import com.example.bibliogest.repository.BookRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BookViewModel(application: Application) : AndroidViewModel(application) {
    private val database = BookDatabase.getDatabase(application)
    private val bookRepository = BookRepository(database.bookDao())
    private val authorRepository = AuthorRepository(database.authorDao())

    // --- Livres ---
    private val _books = MutableStateFlow<List<Book>>(emptyList())
    val books: StateFlow<List<Book>> = _books

    private val _favoriteBooks = MutableStateFlow<List<Book>>(emptyList())
    val favoriteBooks: StateFlow<List<Book>> = _favoriteBooks

    // --- Auteurs ---
    private val _authors = MutableStateFlow<List<Author>>(emptyList())
    val authors: StateFlow<List<Author>> = _authors

    init {
        loadBooks()
        loadFavoriteBooks()
        loadAuthors()
    }

    private fun loadBooks() = viewModelScope.launch {
        bookRepository.getAllBooks().collect { _books.value = it }
    }

    private fun loadFavoriteBooks() = viewModelScope.launch {
        bookRepository.getFavoriteBooks().collect { _favoriteBooks.value = it }
    }

    private fun loadAuthors() = viewModelScope.launch {
        authorRepository.getAllAuthors().collect { _authors.value = it }
    }

    // --- Méthodes pour les livres ---
    fun addBook(book: Book) = viewModelScope.launch {
        bookRepository.insertBook(book)
    }

    fun updateBook(book: Book) = viewModelScope.launch {
        bookRepository.updateBook(book)
    }

    fun deleteBook(book: Book) = viewModelScope.launch {
        bookRepository.deleteBook(book)
    }

    suspend fun getBookById(id: Int): Book? = bookRepository.getBookById(id)

    fun toggleFavorite(book: Book) = viewModelScope.launch {
        bookRepository.toggleFavorite(book.id, !book.isFavorite)
    }
}
