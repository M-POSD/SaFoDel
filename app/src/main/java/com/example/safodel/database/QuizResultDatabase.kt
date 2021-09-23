package com.example.safodel.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.safodel.dao.QuizResultDAO
import com.example.safodel.entity.QuizResult
import com.example.safodel.util.DateLongConverter

@Database(entities = [QuizResult::class], version = 1, exportSchema = false)
@TypeConverters(DateLongConverter::class)
abstract class QuizResultDatabase : RoomDatabase() {
    abstract fun quizResultDAO(): QuizResultDAO

    companion object {
        // Volatile annotation means any writes to this method will be visible to other threads
        // and guarantees visibility of changes to variables across threads
        @Volatile
        private var INSTANCE: QuizResultDatabase? = null
        fun getInstance(context: Context): QuizResultDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext, QuizResultDatabase::class.java,
                    "CustomerDatabase"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}