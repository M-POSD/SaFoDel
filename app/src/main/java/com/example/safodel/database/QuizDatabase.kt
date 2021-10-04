package com.example.safodel.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.safodel.dao.TimeEntryWithQuizResultDAO
import com.example.safodel.entity.QuizResult
import com.example.safodel.entity.TimeEntry
import com.example.safodel.util.DateLongConverter

// type converters for date and long switching when writing in and reading out from the database
@Database(entities = [QuizResult::class, TimeEntry::class], version = 1, exportSchema = false)
@TypeConverters(DateLongConverter::class)
abstract class QuizDatabase : RoomDatabase() {
    abstract fun timeEntryWithQuizResultDAO(): TimeEntryWithQuizResultDAO

    companion object {
        // Volatile annotation means any writes to this method will be visible to other threads
        // and guarantees visibility of changes to variables across threads
        @Volatile
        private var INSTANCE: QuizDatabase? = null

        fun getInstance(context: Context): QuizDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext, QuizDatabase::class.java,
                    "QuizDatabase"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}