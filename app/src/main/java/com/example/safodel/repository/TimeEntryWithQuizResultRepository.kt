package com.example.safodel.repository

import android.app.Application
import android.util.Log
import androidx.annotation.WorkerThread
import com.example.safodel.dao.TimeEntryWithQuizResultDAO
import com.example.safodel.database.QuizDatabase
import com.example.safodel.entity.QuizResult
import com.example.safodel.entity.TimeEntry
import com.example.safodel.entity.TimeEntryWithQuizResult
import kotlinx.coroutines.flow.Flow

class TimeEntryWithQuizResultRepository(private val application: Application) {
    var timeEntryWithQuizResultDAO: TimeEntryWithQuizResultDAO
    init{
        val db: QuizDatabase = QuizDatabase.getInstance(application)
        timeEntryWithQuizResultDAO = db.timeEntryWithQuizResultDAO()
    }

    val allResults: Flow<List<TimeEntryWithQuizResult>> = timeEntryWithQuizResultDAO.getAllResults()

    @WorkerThread
    suspend fun addTimeEntryWithQuizResults(timeEntry: TimeEntry, quizResults: List<QuizResult>) {
        timeEntryWithQuizResultDAO.addTimeEntryWithQuizResults(timeEntry, quizResults)
    }

    @WorkerThread
    suspend fun deleteTimeEntryWithQuizResults(timeEntry: TimeEntry, quizResults: List<QuizResult>) {
        timeEntryWithQuizResultDAO.deleteTimeEntryWithQuizResults(timeEntry, quizResults)
    }

    @WorkerThread
    suspend fun deleteAllTimeEntry() {
        timeEntryWithQuizResultDAO.deleteAllTimeEntry()
    }

    @WorkerThread
    suspend fun findById(timeId: Int): TimeEntryWithQuizResult? {
        return timeEntryWithQuizResultDAO.findByID(timeId)
    }

    @WorkerThread
    suspend fun findByTimeEntry(timeEntry: Long): TimeEntryWithQuizResult? {
        return timeEntryWithQuizResultDAO.findByTimeEntry(timeEntry)
    }
}