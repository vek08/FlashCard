package com.flashcard.app.domain.repository

import com.flashcard.app.data.local.entity.Flashcard
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for flashcard data operations.
 *
 * Defines the contract for accessing and manipulating flashcard data,
 * abstracting the underlying data source from the domain and presentation layers.
 * Implementations of this interface handle the actual data persistence logic.
 */
interface FlashcardRepository {

    /**
     * Retrieves all flashcards as a reactive stream.
     *
     * @return A [Flow] emitting the complete list of flashcards whenever the data changes.
     */
    fun getAllFlashcards(): Flow<List<Flashcard>>

    /**
     * Retrieves a single flashcard by its unique identifier.
     *
     * @param id The unique identifier of the flashcard.
     * @return A [Flow] emitting the flashcard if found, or null otherwise.
     */
    fun getFlashcardById(id: Int): Flow<Flashcard?>

    /**
     * Inserts a new flashcard into the data store.
     *
     * @param flashcard The [Flashcard] to insert.
     */
    suspend fun insertFlashcard(flashcard: Flashcard)

    /**
     * Updates an existing flashcard in the data store.
     *
     * @param flashcard The [Flashcard] with updated fields.
     */
    suspend fun updateFlashcard(flashcard: Flashcard)

    /**
     * Deletes a flashcard from the data store.
     *
     * @param flashcard The [Flashcard] to delete.
     */
    suspend fun deleteFlashcard(flashcard: Flashcard)
}
