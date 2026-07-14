package com.flashcard.app.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.flashcard.app.data.local.entity.Flashcard
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for the [Flashcard] entity.
 *
 * Provides methods for performing CRUD operations on the flashcards table.
 * Query methods return [Flow] to support reactive data observation.
 */
@Dao
interface FlashcardDao {

    /**
     * Retrieves all flashcards ordered by creation date (newest first).
     *
     * @return A [Flow] emitting the list of all flashcards whenever the data changes.
     */
    @Query("SELECT * FROM flashcards ORDER BY createdAt DESC")
    fun getAllFlashcards(): Flow<List<Flashcard>>

    /**
     * Retrieves a single flashcard by its unique identifier.
     *
     * @param id The unique identifier of the flashcard to retrieve.
     * @return A [Flow] emitting the flashcard if found, or null otherwise.
     */
    @Query("SELECT * FROM flashcards WHERE id = :id")
    fun getFlashcardById(id: Int): Flow<Flashcard?>

    /**
     * Inserts a new flashcard into the database.
     * If a flashcard with the same primary key already exists, it will be replaced.
     *
     * @param flashcard The [Flashcard] entity to insert.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFlashcard(flashcard: Flashcard)

    /**
     * Updates an existing flashcard in the database.
     *
     * @param flashcard The [Flashcard] entity with updated fields.
     */
    @Update
    suspend fun updateFlashcard(flashcard: Flashcard)

    /**
     * Deletes a flashcard from the database.
     *
     * @param flashcard The [Flashcard] entity to delete.
     */
    @Delete
    suspend fun deleteFlashcard(flashcard: Flashcard)
}
