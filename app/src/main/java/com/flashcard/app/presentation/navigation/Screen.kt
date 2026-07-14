package com.flashcard.app.presentation.navigation

/**
 * Sealed class representing all navigation destinations in the app.
 * Each destination defines its route pattern for use with Jetpack Navigation.
 */
sealed class Screen(val route: String) {
    /** Home screen displaying flashcards one at a time */
    data object Home : Screen("home")

    /** Screen for adding a new flashcard */
    data object AddFlashcard : Screen("add_flashcard")

    /** Screen for editing an existing flashcard. Requires flashcard ID as argument. */
    data object EditFlashcard : Screen("edit_flashcard/{flashcardId}") {
        fun createRoute(flashcardId: Int) = "edit_flashcard/$flashcardId"
    }
}
