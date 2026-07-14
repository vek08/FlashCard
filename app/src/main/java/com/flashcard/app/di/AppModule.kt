package com.flashcard.app.di

import android.content.Context
import androidx.room.Room
import com.flashcard.app.data.local.dao.FlashcardDao
import com.flashcard.app.data.local.database.FlashcardDatabase
import com.flashcard.app.data.repository.FlashcardRepositoryImpl
import com.flashcard.app.domain.repository.FlashcardRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt dependency injection module for the FlashCard application.
 *
 * Provides singleton instances of the database, DAO, and repository
 * to be injected throughout the app. Uses [Binds] for the repository
 * interface-to-implementation mapping and [Provides] for concrete
 * object construction.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    /**
     * Binds the [FlashcardRepositoryImpl] to the [FlashcardRepository] interface.
     * This allows consumers to depend on the abstraction rather than the implementation.
     *
     * @param impl The concrete repository implementation.
     * @return The [FlashcardRepository] interface backed by the implementation.
     */
    @Binds
    @Singleton
    abstract fun bindFlashcardRepository(
        impl: FlashcardRepositoryImpl
    ): FlashcardRepository

    companion object {

        /**
         * Provides a singleton instance of the [FlashcardDatabase].
         * The database is built with a [FlashcardDatabase.PrepopulateCallback]
         * that seeds sample flashcards on first creation.
         *
         * @param context The application context used by Room.
         * @return The singleton [FlashcardDatabase] instance.
         */
        @Provides
        @Singleton
        fun provideFlashcardDatabase(
            @ApplicationContext context: Context
        ): FlashcardDatabase {
            return Room.databaseBuilder(
                context,
                FlashcardDatabase::class.java,
                "flashcard_database"
            )
                .addCallback(FlashcardDatabase.PrepopulateCallback())
                .build()
        }

        /**
         * Provides the [FlashcardDao] from the database instance.
         *
         * @param database The [FlashcardDatabase] to extract the DAO from.
         * @return The [FlashcardDao] for performing database operations.
         */
        @Provides
        @Singleton
        fun provideFlashcardDao(
            database: FlashcardDatabase
        ): FlashcardDao {
            return database.flashcardDao()
        }
    }
}
