package com.flashcard.app.presentation.navigation


sealed class Screen(val route: String) {
    
    data object Home : Screen("home")

    
    data object AddFlashcard : Screen("add_flashcard")

    
    data object EditFlashcard : Screen("edit_flashcard/{flashcardId}") {
        fun createRoute(flashcardId: Int) = "edit_flashcard/$flashcardId"
    }
}
