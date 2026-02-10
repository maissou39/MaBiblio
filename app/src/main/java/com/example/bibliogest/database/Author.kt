package com.example.bibliogest.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "authors")
data class Author(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nom: String,
    val prenom: String
)
