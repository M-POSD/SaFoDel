package com.example.safodel.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.safodel.entity.QuizResult
import com.example.safodel.entity.TimeEntry
import com.example.safodel.entity.TimeEntryWithQuizResult
import com.example.safodel.repository.TimeEntryWithQuizResultRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TimeEntryWithQuizResultViewModel(application: Application): AndroidViewModel(application) {
    private val repository: TimeEntryWithQuizResultRepository = TimeEntryWithQuizResultRepository(application)

    var allQuizResults: LiveData<List<TimeEntryWithQuizResult>> =
        repository.allResults.asLiveData()

    fun addTimeEntryWithQuizResults(timeEntry: TimeEntry, quizResults: List<QuizResult>) =
        viewModelScope.launch(Dispatchers.IO) {
        repository.addTimeEntryWithQuizResults(timeEntry, quizResults)
    }

    fun deleteTimeEntryWithQuizResults(timeEntry: TimeEntry, quizResults: List<QuizResult>) =
        viewModelScope.launch(Dispatchers.IO) {
        repository.deleteTimeEntryWithQuizResults(timeEntry, quizResults)
    }

    fun deleteAllTimeEntry() = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteAllTimeEntry()
    }

    suspend fun findQuizResultById(id: Int): TimeEntryWithQuizResult? {
        return viewModelScope.async(Dispatchers.IO) {
            repository.findById(id)
        }.await()
    }

    suspend fun findQuizResultByTimeEntry(timeEntry: Long): TimeEntryWithQuizResult? {
        return withContext(viewModelScope.coroutineContext + Dispatchers.IO) {
            repository.findByTimeEntry(timeEntry)
        }
    }
}