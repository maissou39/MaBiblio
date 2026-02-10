package com.example.bibliogest.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.bibliogest.database.Author
import com.example.bibliogest.database.BookDatabase
import com.example.bibliogest.repository.AuthorRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthorViewModel(application: Application) : AndroidViewModel(application) {
    private val database = BookDatabase.getDatabase(application)
    private val repository = AuthorRepository(database.authorDao())

    private val _authors = MutableStateFlow<List<Author>>(emptyList())
    val authors: StateFlow<List<Author>> = _authors

    init {
        loadAuthors()
    }

    fun loadAuthors() = viewModelScope.launch {
        repository.getAllAuthors().collect {
            _authors.value = it
        }
    }

    fun addAuthor(firstName: String, lastName: String) = viewModelScope.launch {
        try {
            val newAuthor = Author(prenom = firstName, nom = lastName)
            repository.insertAuthor(newAuthor)
        } catch (e: Exception) {
            println("Erreur lors de l'ajout de l'auteur: ${e.message}")
        }
    }
}
