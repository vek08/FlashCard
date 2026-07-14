package com.flashcard.app.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.flashcard.app.data.local.dao.FlashcardDao
import com.flashcard.app.data.local.entity.Flashcard


@Database(entities = [Flashcard::class], version = 1, exportSchema = true)
abstract class FlashcardDatabase : RoomDatabase() {

    
    abstract fun flashcardDao(): FlashcardDao

    
    class PrepopulateCallback : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            val sampleCards = listOf(
                Pair(
                    "What is Kotlin?",
                    "Kotlin is a modern, statically-typed programming language developed by JetBrains that runs on the JVM and can be compiled to JavaScript or native code."
                ),
                Pair(
                    "What is Jetpack Compose?",
                    "Jetpack Compose is Android's modern toolkit for building native UI using a declarative approach with Kotlin."
                ),
                Pair(
                    "What does MVVM stand for?",
                    "MVVM stands for Model-View-ViewModel, an architectural pattern that separates the UI from business logic."
                ),
                Pair(
                    "What is a Coroutine in Kotlin?",
                    "A coroutine is a concurrency design pattern that allows you to write asynchronous, non-blocking code in a sequential manner."
                ),
                Pair(
                    "What is Room Database?",
                    "Room is a persistence library that provides an abstraction layer over SQLite, enabling robust database access while leveraging SQLite's full power."
                )
            )

            sampleCards.forEach { (question, answer) ->
                db.execSQL(
                    "INSERT INTO flashcards (question, answer, createdAt) VALUES (?, ?, ?)",
                    arrayOf(question, answer, System.currentTimeMillis())
                )
            }
        }
    }
}
