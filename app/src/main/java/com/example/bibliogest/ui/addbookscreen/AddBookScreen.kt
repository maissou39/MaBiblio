package com.example.bibliogest.ui.addbookscreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bibliogest.database.Book
import com.example.bibliogest.database.BookState
import com.example.bibliogest.model.BookViewModel
import com.example.bibliogest.ui.components.BasicInputField
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditBookScreen(
    bookId: Int = 0,
    viewModel: BookViewModel,
    onBackClick: () -> Unit,
    onAddAuthorClick: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val authors by viewModel.authors.collectAsState()

    var title by remember { mutableStateOf("") }
    var selectedAuthorId by remember { mutableStateOf<Int?>(null) }
    var publicationYear by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var state by remember { mutableStateOf(BookState.INCONNU) }

    var isEditMode by remember { mutableStateOf(false) }
    var titleError by remember { mutableStateOf(false) }
    var authorError by remember { mutableStateOf(false) }
    var yearError by remember { mutableStateOf(false) }

    LaunchedEffect(bookId) {
        if (bookId > 0) {
            isEditMode = true
            viewModel.getBookById(bookId)?.let { book ->
                title = book.title
                selectedAuthorId = book.authorId
                publicationYear = book.publicationYear?.toString() ?: ""
                description = book.description ?: ""
                state = book.state
            }
        }
    }

    fun saveBook() {
        titleError = title.isBlank()
        authorError = selectedAuthorId == null
        
        val yearAsInt = publicationYear.toIntOrNull()
        yearError = publicationYear.isNotBlank() && yearAsInt == null

        if (titleError || authorError || yearError) return

        val bookToSave = Book(
            id = if (isEditMode) bookId else 0,
            title = title,
            authorId = selectedAuthorId!!,
            publicationYear = yearAsInt,
            description = description.ifBlank { null },
            state = state
        )

        coroutineScope.launch {
            if (isEditMode) viewModel.updateBook(bookToSave) else viewModel.addBook(bookToSave)
            onBackClick()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (isEditMode) "Modifier le livre" else "Ajouter un livre") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) { Icon(Icons.Default.ArrowBack, contentDescription = "Retour") }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Ligne 89 fixée
            BasicInputField(
                value = title, 
                onValueChange = { title = it }, 
                label = "Titre", 
                isError = titleError,
                labelError = "Le titre est obligatoire"
            )

            // Sélection de l'auteur
            Row(verticalAlignment = Alignment.CenterVertically) {
                var expanded by remember { mutableStateOf(false) }


                //Liste Deroulante des auteurs
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded },
                    modifier = Modifier.weight(1f)
                ) {
                    OutlinedTextField( // Champs de saisie

                        //On affiche le nom et le prénom de l'auteur qui existe deja si on en a pas on affiche rien -> ""
                        value = authors.find { it.id == selectedAuthorId }?.let { "${it.prenom} ${it.nom}" } ?: "",
                        onValueChange = {}, //fonction vide car l'user va juste choisir le nom de l'auteur
                        readOnly = true, //l'utilisateur ne tape rien, il choisit
                        label = { Text("Auteur") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) }, //icon triangle qui change lorsquon ouvre/ferme la liste
                        modifier = Modifier.menuAnchor(), //liaison du champs au menu deroulant

                    )

                    ExposedDropdownMenu( //Menu deroulant
                        expanded = expanded,  //controle si menu ouvert/fermer
                        onDismissRequest = {
                            expanded = false //ferme le menu si l'user clique en dehors
                        }) {
                        authors.forEach { author ->   //Boucle qui crée un élément de menu pour chaque auteur
                            DropdownMenuItem(
                                text = { Text("${author.prenom} ${author.nom}") }, //affiche les auteurs
                                onClick = {
                                    selectedAuthorId = author.id //enregistre l'id de l'auteur choisi
                                    expanded = false //ferme le menu
                                }
                            )
                        }
                    }
                }
                //Bouton "+" pour ajouter un auteur
                IconButton(onClick = onAddAuthorClick) {
                    Icon(Icons.Default.Add, contentDescription = "Ajouter un auteur")
                }
            }

            BasicInputField(
                value = publicationYear, 
                onValueChange = { publicationYear = it }, 
                label = "Année de publication",
                isError = yearError,
                labelError = "L'année doit être un nombre valide"
            )
            
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth().height(120.dp)
            )

            // Sélection de l'état
            Text("État du livre:", fontSize = 16.sp)
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                listOf(BookState.NEUF, BookState.ANCIEN).forEach { bookState ->
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable { state = bookState }) {
                        RadioButton(selected = state == bookState, onClick = { state = bookState })
                        Text(text = bookState.name, modifier = Modifier.padding(start = 4.dp))
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))
            Button(onClick = { saveBook() }, modifier = Modifier.fillMaxWidth()) {
                Text(if (isEditMode) "Mettre à jour" else "Sauvegarder")
            }
        }
    }
}
