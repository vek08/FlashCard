package com.flashcard.app.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flashcard.app.data.local.entity.Flashcard
import com.flashcard.app.domain.repository.FlashcardRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * UI state for the Home screen.
 * Contains the full list of flashcards and current navigation position.
 */
data class HomeUiState(
    val flashcards: List<Flashcard> = emptyList(),
    val currentIndex: Int = 0,
    val isAnswerVisible: Boolean = false,
    val isLoading: Boolean = true
) {
    /** The currently displayed flashcard, or null if the list is empty. */
    val currentFlashcard: Flashcard?
        get() = flashcards.getOrNull(currentIndex)

    /** Whether there is a next flashcard to navigate to. */
    val hasNext: Boolean
        get() = currentIndex < flashcards.size - 1

    /** Whether there is a previous flashcard to navigate to. */
    val hasPrevious: Boolean
        get() = currentIndex > 0

    /** Human-readable card position indicator (e.g., "3 / 10"). */
    val cardPosition: String
        get() = if (flashcards.isEmpty()) "0 / 0" else "${currentIndex + 1} / ${flashcards.size}"
}

/**
 * ViewModel for the Home screen.
 * Manages flashcard navigation, answer visibility, and deletion.
 * Observes the database reactively via Flow so the UI stays in sync.
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: FlashcardRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        // Observe all flashcards from the database reactively
        repository.getAllFlashcards()
            .onEach { flashcards ->
                _uiState.update { state ->
                    val newIndex = state.currentIndex.coerceIn(
                        0,
                        (flashcards.size - 1).coerceAtLeast(0)
                    )
                    state.copy(
                        flashcards = flashcards,
                        currentIndex = newIndex,
                        isLoading = false,
                        // Reset answer visibility when the card changes due to list update
                        isAnswerVisible = if (newIndex != state.currentIndex) false else state.isAnswerVisible
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    /** Navigate to the next flashcard, hiding the answer. */
    fun nextCard() {
        _uiState.update { state ->
            if (state.hasNext) {
                state.copy(
                    currentIndex = state.currentIndex + 1,
                    isAnswerVisible = false
                )
            } else state
        }
    }

    /** Navigate to the previous flashcard, hiding the answer. */
    fun previousCard() {
        _uiState.update { state ->
            if (state.hasPrevious) {
                state.copy(
                    currentIndex = state.currentIndex - 1,
                    isAnswerVisible = false
                )
            } else state
        }
    }

    /** Toggle the visibility of the current card's answer. */
    fun toggleAnswer() {
        _uiState.update { it.copy(isAnswerVisible = !it.isAnswerVisible) }
    }

    /** Delete a flashcard from the repository. */
    fun deleteFlashcard(flashcard: Flashcard) {
        viewModelScope.launch {
            repository.deleteFlashcard(flashcard)
        }
    }
}
