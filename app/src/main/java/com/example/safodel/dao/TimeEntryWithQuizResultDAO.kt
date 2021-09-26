package com.example.safodel.dao

import androidx.room.*
import com.example.safodel.entity.QuizResult
import com.example.safodel.entity.TimeEntry
import com.example.safodel.entity.TimeEntryWithQuizResult
import kotlinx.coroutines.flow.Flow

@Dao
interface TimeEntryWithQuizResultDAO {
    @Transaction
    @Query("SELECT * FROM time_entry ORDER BY time DESC")
    fun getAllResults(): Flow<List<TimeEntryWithQuizResult>>

    @Transaction
    @Query("SELECT * FROM time_entry WHERE uid = :timeId LIMIT 1")
    suspend fun findByID(timeId: Int): TimeEntryWithQuizResult?

    @Transaction
    @Query("SELECT * FROM time_entry WHERE time = :timeEntry LIMIT 1")
    suspend fun findByTimeEntry(timeEntry: Long): TimeEntryWithQuizResult?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTimeEntry(timeEntry: TimeEntry): Long

    @Delete
    suspend fun deleteTimeEntry(timeEntry: TimeEntry): Int

    @Update
    suspend fun updateTimeEntry(timeEntry: TimeEntry)

    @Query("DELETE FROM time_entry")
    suspend fun deleteAllTimeEntry()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addQuizResults(quizResults: List<QuizResult>)

    @Insert
    suspend fun deleteQuizResults(quizResults: List<QuizResult>)

    @Transaction
    @Insert
    suspend fun addTimeEntryWithQuizResults(timeEntry: TimeEntry, quizResults: List<QuizResult>) {
        val id = addTimeEntry(timeEntry)
        quizResults.forEach { it.timeEntryId = id.toInt() }
        addQuizResults(quizResults)
    }

    @Transaction
    @Delete
    suspend fun deleteTimeEntryWithQuizResults(timeEntry: TimeEntry, quizResults: List<QuizResult>) {
        val id = deleteTimeEntry(timeEntry)
        quizResults.forEach { it.timeEntryId = id }
        deleteQuizResults(quizResults)
    }
}