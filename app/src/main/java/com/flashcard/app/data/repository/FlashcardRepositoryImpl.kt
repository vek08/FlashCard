package com.flashcard.app.data.repository

import com.flashcard.app.data.local.dao.FlashcardDao
import com.flashcard.app.data.local.entity.Flashcard
import com.flashcard.app.domain.repository.FlashcardRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Implementation of [FlashcardRepository] backed by a Room database.
 *
 * Delegates all data operations to the [FlashcardDao], acting as a single
 * source of truth for flashcard data throughout the application.
 *
 * @property dao The [FlashcardDao] used for database operations.
 */
class FlashcardRepositoryImpl @Inject constructor(
    private val dao: FlashcardDao
) : FlashcardRepository {

    /**
     * Retrieves all flashcards from the database as a reactive stream.
     *
     * @return A [Flow] emitting the complete list of flashcards ordered by creation date.
     */
    override fun getAllFlashcards(): Flow<List<Flashcard>> {
        return dao.getAllFlashcards()
    }

    /**
     * Retrieves a single flashcard by its unique identifier.
     *
     * @param id The unique identifier of the flashcard.
     * @return A [Flow] emitting the flashcard if found, or null otherwise.
     */
    override fun getFlashcardById(id: Int): Flow<Flashcard?> {
        return dao.getFlashcardById(id)
    }

    /**
     * Inserts a new flashcard into the database.
     *
     * @param flashcard The [Flashcard] to insert.
     */
    override suspend fun insertFlashcard(flashcard: Flashcard) {
        dao.insertFlashcard(flashcard)
    }

    /**
     * Updates an existing flashcard in the database.
     *
     * @param flashcard The [Flashcard] with updated fields.
     */
    override suspend fun updateFlashcard(flashcard: Flashcard) {
        dao.updateFlashcard(flashcard)
    }

    /**
     * Deletes a flashcard from the database.
     *
     * @param flashcard The [Flashcard] to delete.
     */
    override suspend fun deleteFlashcard(flashcard: Flashcard) {
        dao.deleteFlashcard(flashcard)
    }
}
