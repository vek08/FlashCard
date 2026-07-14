package com.flashcard.app.presentation.addEdit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flashcard.app.data.local.entity.Flashcard
import com.flashcard.app.domain.repository.FlashcardRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * UI state for the Add/Edit screen.
 * Tracks form fields, validation errors, and save progress.
 */
data class AddEditUiState(
    val question: String = "",
    val answer: String = "",
    val isEditing: Boolean = false,
    val questionError: String? = null,
    val answerError: String? = null,
    val isSaving: Boolean = false
)

/**
 * One-time events emitted by the Add/Edit screen.
 * Used for navigation triggers and error display that should not survive recomposition.
 */
sealed class AddEditEvent {
    /** Flashcard was saved successfully — navigate back. */
    data object SaveSuccess : AddEditEvent()

    /** An error occurred while saving. */
    data class SaveError(val message: String) : AddEditEvent()
}

/**
 * ViewModel for the Add/Edit Flashcard screen.
 * Handles form state, input validation, and persisting flashcards.
 * Automatically detects edit mode when a flashcardId is present in SavedStateHandle.
 */
@HiltViewModel
class AddEditViewModel @Inject constructor(
    private val repository: FlashcardRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val flashcardId: Int? = savedStateHandle.get<Int>("flashcardId")

    private val _uiState = MutableStateFlow(AddEditUiState())
    val uiState: StateFlow<AddEditUiState> = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<AddEditEvent>()
    val events = _events.asSharedFlow()

    private var existingFlashcard: Flashcard? = null

    init {
        // If editing, load the existing flashcard data into the form
        flashcardId?.let { id ->
            viewModelScope.launch {
                repository.getFlashcardById(id).firstOrNull()?.let { flashcard ->
                    existingFlashcard = flashcard
                    _uiState.update {
                        it.copy(
                            question = flashcard.question,
                            answer = flashcard.answer,
                            isEditing = true
                        )
                    }
                }
            }
        }
    }

    /** Update the question text and clear any previous validation error. */
    fun onQuestionChanged(question: String) {
        _uiState.update { it.copy(question = question, questionError = null) }
    }

    /** Update the answer text and clear any previous validation error. */
    fun onAnswerChanged(answer: String) {
        _uiState.update { it.copy(answer = answer, answerError = null) }
    }

    /** Validate inputs and save (insert or update) the flashcard. */
    fun saveFlashcard() {
        val state = _uiState.value

        // Validate inputs
        val questionError = if (state.question.isBlank()) "Question cannot be empty" else null
        val answerError = if (state.answer.isBlank()) "Answer cannot be empty" else null

        if (questionError != null || answerError != null) {
            _uiState.update {
                it.copy(questionError = questionError, answerError = answerError)
            }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isSaving = true) }
            try {
                if (existingFlashcard != null) {
                    // Update existing flashcard
                    repository.updateFlashcard(
                        existingFlashcard!!.copy(
                            question = state.question.trim(),
                            answer = state.answer.trim()
                        )
                    )
                } else {
                    // Insert new flashcard
                    repository.insertFlashcard(
                        Flashcard(
                            question = state.question.trim(),
                            answer = state.answer.trim()
                        )
                    )
                }
                _events.emit(AddEditEvent.SaveSuccess)
            } catch (e: Exception) {
                _events.emit(AddEditEvent.SaveError(e.message ?: "Failed to save flashcard"))
            } finally {
                _uiState.update { it.copy(isSaving = false) }
            }
        }
    }
}
