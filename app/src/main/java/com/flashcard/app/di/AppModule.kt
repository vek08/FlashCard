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


@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    
    @Binds
    @Singleton
    abstract fun bindFlashcardRepository(
        impl: FlashcardRepositoryImpl
    ): FlashcardRepository

    companion object {

        
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

        
        @Provides
        @Singleton
        fun provideFlashcardDao(
            database: FlashcardDatabase
        ): FlashcardDao {
            return database.flashcardDao()
        }
    }
}
