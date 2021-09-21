package com.example.safodel.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WeatherViewModel : ViewModel() {
    private var currentWeather = MutableLiveData<String>()

    fun  getWeather(): LiveData<String> {
        return currentWeather
    }

    fun setWeather(weather: String) {
        currentWeather.value = weather
    }
}