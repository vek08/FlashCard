package com.flashcard.app.data.repository

import com.flashcard.app.data.local.dao.FlashcardDao
import com.flashcard.app.data.local.entity.Flashcard
import com.flashcard.app.domain.repository.FlashcardRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class FlashcardRepositoryImpl @Inject constructor(
    private val dao: FlashcardDao
) : FlashcardRepository {

    
    override fun getAllFlashcards(): Flow<List<Flashcard>> {
        return dao.getAllFlashcards()
    }

    
    override fun getFlashcardById(id: Int): Flow<Flashcard?> {
        return dao.getFlashcardById(id)
    }

    
    override suspend fun insertFlashcard(flashcard: Flashcard) {
        dao.insertFlashcard(flashcard)
    }

    
    override suspend fun updateFlashcard(flashcard: Flashcard) {
        dao.updateFlashcard(flashcard)
    }

    
    override suspend fun deleteFlashcard(flashcard: Flashcard) {
        dao.deleteFlashcard(flashcard)
    }
}
