package com.example.safodel.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.safodel.entity.QuizResult

class HistoryDetailViewModel: ViewModel() {
    private var result = MutableLiveData<QuizResult>()

    fun getResult(): LiveData<QuizResult> {
        return result
    }

    fun setResult(quizResult: QuizResult) {
        result.value = quizResult
    }
}