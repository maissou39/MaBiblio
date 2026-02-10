package com.example.bibliogest.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.bibliogest.model.BookViewModel
import com.example.bibliogest.model.AuthorViewModel
import com.example.bibliogest.ui.bookdetailscreen.BookDetailScreen
import com.example.bibliogest.ui.booklistscreen.BookListScreen
import com.example.bibliogest.ui.addbookscreen.AddEditBookScreen
import com.example.bibliogest.ui.addauthorscreen.AddAuthorScreen


// Routes de navigation
const val BOOKS_ROUTE = "books"
const val BOOK_DETAIL_ROUTE = "book_detail"
const val ADD_BOOK_ROUTE = "add_book"
const val EDIT_BOOK_ROUTE = "edit_book"
const val ADD_AUTHOR_ROUTE = "add_author"

@Composable
fun NavGraph(navController: NavHostController = rememberNavController()) {
    val bookViewModel: BookViewModel = viewModel()
    val authorViewModel: AuthorViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = BOOKS_ROUTE
    ) {
        // Écran liste des livres
        composable(route = BOOKS_ROUTE) {
            BookListScreen(
                viewModel = bookViewModel,
                onBookClick = { bookId ->

                    navController.navigate("$BOOK_DETAIL_ROUTE/$bookId")
                },
                onAddClick = {
                    navController.navigate(ADD_BOOK_ROUTE)
                }
            )
        }
        // Route: Ajout d'un livre
        composable(route = ADD_BOOK_ROUTE) {
            AddEditBookScreen(
                onBackClick = { navController.popBackStack() },
                viewModel = bookViewModel,
                onAddAuthorClick = { navController.navigate(ADD_AUTHOR_ROUTE) }
            )
        }
        // Route: Modification d'un livre
        composable(route = "$EDIT_BOOK_ROUTE/{bookId}",
            arguments = listOf(
                navArgument("bookId") { type = NavType.IntType }
            )
        ) {
            AddEditBookScreen(
                bookId = it.arguments?.getInt("bookId") ?: 0,
                onBackClick = { navController.popBackStack() },
                viewModel = bookViewModel,
                onAddAuthorClick = { navController.navigate(ADD_AUTHOR_ROUTE) }
            )
        }
        // Écran détail d'un livre
        composable(
            route = "$BOOK_DETAIL_ROUTE/{bookId}",
            arguments = listOf(
                navArgument("bookId") { type = NavType.IntType }
            )
        ) {
            BookDetailScreen(
                bookId = it.arguments?.getInt("bookId") ?: 0,
                onBackClick = { navController.popBackStack() },
                viewModel = bookViewModel,
                onEditClick = { bookId ->
                    navController.navigate("$EDIT_BOOK_ROUTE/$bookId")
                }
            )
        }
        // Route: Ajout d'un auteur
        composable(route = ADD_AUTHOR_ROUTE) {
            AddAuthorScreen(
                viewModel = authorViewModel,
                onAuthorAdded = { navController.popBackStack() }
            )
        }

    }
}
