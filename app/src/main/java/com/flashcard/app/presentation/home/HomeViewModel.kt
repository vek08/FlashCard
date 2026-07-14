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


data class HomeUiState(
    val flashcards: List<Flashcard> = emptyList(),
    val currentIndex: Int = 0,
    val isAnswerVisible: Boolean = false,
    val isLoading: Boolean = true
) {
    
    val currentFlashcard: Flashcard?
        get() = flashcards.getOrNull(currentIndex)

    
    val hasNext: Boolean
        get() = currentIndex < flashcards.size - 1

    
    val hasPrevious: Boolean
        get() = currentIndex > 0

    
    val cardPosition: String
        get() = if (flashcards.isEmpty()) "0 / 0" else "${currentIndex + 1} / ${flashcards.size}"
}


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: FlashcardRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        
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
                        
                        isAnswerVisible = if (newIndex != state.currentIndex) false else state.isAnswerVisible
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    
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

    
    fun toggleAnswer() {
        _uiState.update { it.copy(isAnswerVisible = !it.isAnswerVisible) }
    }

    
    fun deleteFlashcard(flashcard: Flashcard) {
        viewModelScope.launch {
            repository.deleteFlashcard(flashcard)
        }
    }
}
