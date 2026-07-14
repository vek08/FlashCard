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


data class AddEditUiState(
    val question: String = "",
    val answer: String = "",
    val isEditing: Boolean = false,
    val questionError: String? = null,
    val answerError: String? = null,
    val isSaving: Boolean = false
)


sealed class AddEditEvent {
    
    data object SaveSuccess : AddEditEvent()

    
    data class SaveError(val message: String) : AddEditEvent()
}


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

    
    fun onQuestionChanged(question: String) {
        _uiState.update { it.copy(question = question, questionError = null) }
    }

    
    fun onAnswerChanged(answer: String) {
        _uiState.update { it.copy(answer = answer, answerError = null) }
    }

    
    fun saveFlashcard() {
        val state = _uiState.value

        
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
                    
                    repository.updateFlashcard(
                        existingFlashcard!!.copy(
                            question = state.question.trim(),
                            answer = state.answer.trim()
                        )
                    )
                } else {
                    
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
