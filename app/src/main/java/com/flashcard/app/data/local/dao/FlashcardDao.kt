package com.flashcard.app.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.flashcard.app.data.local.entity.Flashcard
import kotlinx.coroutines.flow.Flow


@Dao
interface FlashcardDao {

    
    @Query("SELECT * FROM flashcards ORDER BY createdAt DESC")
    fun getAllFlashcards(): Flow<List<Flashcard>>

    
    @Query("SELECT * FROM flashcards WHERE id = :id")
    fun getFlashcardById(id: Int): Flow<Flashcard?>

    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFlashcard(flashcard: Flashcard)

    
    @Update
    suspend fun updateFlashcard(flashcard: Flashcard)

    
    @Delete
    suspend fun deleteFlashcard(flashcard: Flashcard)
}
