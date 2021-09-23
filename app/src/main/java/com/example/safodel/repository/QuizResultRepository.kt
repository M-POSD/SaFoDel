package com.example.safodel.repository

import android.app.Application
import androidx.annotation.WorkerThread
import com.example.safodel.dao.QuizResultDAO
import com.example.safodel.database.QuizResultDatabase
import com.example.safodel.entity.QuizResult
import kotlinx.coroutines.flow.Flow

class QuizResultRepository(private val application: Application) {
    var quizResultDAO: QuizResultDAO
    init{
        val db: QuizResultDatabase = QuizResultDatabase.getInstance(application)
        quizResultDAO = db.quizResultDAO()
    }

    val allQuizResults: Flow<List<QuizResult>> = quizResultDAO.getAll()

    @WorkerThread
    suspend fun insert(quizResult: QuizResult) {
        quizResultDAO.insert(quizResult)
    }

    @WorkerThread
    suspend fun delete(quizResult: QuizResult) {
        quizResultDAO.delete(quizResult)
    }

    @WorkerThread
    suspend fun deleteAll() {
        quizResultDAO.deleteAll()
    }

    @WorkerThread
    suspend fun findById(quizResultId: Int): QuizResult? {
        return quizResultDAO.findByID(quizResultId)
    }

    @WorkerThread
    suspend fun findByTimeEntry(timeEntry: Long): QuizResult? {
        return quizResultDAO.findByTimeEntry(timeEntry)
    }

    @WorkerThread
    suspend fun update(quizResult: QuizResult) {
        quizResultDAO.update(quizResult)
    }
}