package com.example.safodel.dao

import androidx.room.*
import com.example.safodel.entity.QuizResult
import kotlinx.coroutines.flow.Flow

@Dao
interface QuizResultDAO {
    @Query("SELECT * FROM quiz_result ORDER BY time_entry ASC")
    fun getAll(): Flow<List<QuizResult>>

    @Query("SELECT * FROM quiz_result WHERE uid = :customerId LIMIT 1")
    suspend fun findByID(quizResultId: Int): QuizResult?

    @Query("SELECT * FROM quiz_result WHERE time_entry = :timeEntry LIMIT 1")
    suspend fun findByTimeEntry(timeEntry: Long): QuizResult?

    @Insert
    suspend fun insert(customer: QuizResult)

    @Delete
    suspend fun delete(customer: QuizResult)

    @Update
    suspend fun update(customer: QuizResult)

    @Query("DELETE FROM quiz_result")
    suspend fun deleteAll()
}