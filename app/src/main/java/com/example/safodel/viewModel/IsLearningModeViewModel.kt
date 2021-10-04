package com.example.safodel.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class IsLearningModeViewModel: ViewModel() {
    private var learningMode = MutableLiveData<Boolean>()

    fun  isLearningMode(): LiveData<Boolean> {
        return learningMode
    }

    fun setLearningMode(isLearningMode: Boolean) {
        learningMode.value = isLearningMode
    }
}