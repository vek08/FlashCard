package com.flashcard.app.domain.repository

import com.flashcard.app.data.local.entity.Flashcard
import kotlinx.coroutines.flow.Flow


interface FlashcardRepository {

    
    fun getAllFlashcards(): Flow<List<Flashcard>>

    
    fun getFlashcardById(id: Int): Flow<Flashcard?>

    
    suspend fun insertFlashcard(flashcard: Flashcard)

    
    suspend fun updateFlashcard(flashcard: Flashcard)

    
    suspend fun deleteFlashcard(flashcard: Flashcard)
}
