package com.example.safodel.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.safodel.model.SuburbAccidents
import com.example.safodel.retrofit.SuburbClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MapAccidentViewModel: ViewModel()  {
    val accidentsData = MutableLiveData<SuburbAccidents>()

    fun launchIt(block:suspend CoroutineScope.()->Unit){
        viewModelScope.launch {
            withContext(Dispatchers.IO){ block()}

        }
    }
}