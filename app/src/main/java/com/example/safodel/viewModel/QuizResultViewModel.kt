package com.example.safodel.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.safodel.entity.QuizResult
import com.example.safodel.repository.QuizResultRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class QuizResultViewModel(application: Application): AndroidViewModel(application) {
    private val qrRepository: QuizResultRepository = QuizResultRepository(application)

    var allQuizResults: LiveData<List<QuizResult>> =
        qrRepository.allQuizResults.asLiveData()

    fun insert(quizResult: QuizResult) = viewModelScope.launch(Dispatchers.IO) {
        qrRepository.insert(quizResult)
    }

    fun delete(quizResult: QuizResult) = viewModelScope.launch(Dispatchers.IO) {
        qrRepository.delete(quizResult)
    }

    fun deleteAll() = viewModelScope.launch(Dispatchers.IO) {
        qrRepository.deleteAll()
    }

    suspend fun findQuizResultById(id: Int): QuizResult? {
        return viewModelScope.async(Dispatchers.IO) {
            qrRepository.findById(id)
        }.await()
    }

    suspend fun findQuizResultByTimeEntry(timeEntry: Long): QuizResult? {
        return withContext(viewModelScope.coroutineContext + Dispatchers.IO) {
            qrRepository.findByTimeEntry(timeEntry)
        }
    }

    fun update(quizResult: QuizResult) = viewModelScope.launch(Dispatchers.IO) {
        qrRepository.update(quizResult)
    }
}