package com.example.safodel.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CheckListViewModel : ViewModel() {
    private var checkListPrepared = MutableLiveData<Boolean>()

    fun  getCheck(): LiveData<Boolean> {
        return checkListPrepared
    }

    fun setCheck(isCheck: Boolean) {
        checkListPrepared.value = isCheck
    }
}