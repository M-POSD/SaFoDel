package com.example.safodel.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.safodel.entity.TimeEntryWithQuizResult

class HistoryDetailViewModel: ViewModel() {
    private var result = MutableLiveData<TimeEntryWithQuizResult>()

    fun getResult(): LiveData<TimeEntryWithQuizResult> {
        return result
    }

    fun setResult(timeEntryWithQuizResult: TimeEntryWithQuizResult?) {
        result.value = timeEntryWithQuizResult
    }
}