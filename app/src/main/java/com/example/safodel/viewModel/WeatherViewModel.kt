package com.example.safodel.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.safodel.model.WeatherTemp

class WeatherViewModel : ViewModel() {
    private var currentWeather = MutableLiveData<WeatherTemp>()

    fun  getWeather(): LiveData<WeatherTemp> {
        return currentWeather
    }

    fun setWeather(weather: WeatherTemp) {
        currentWeather.value = weather
    }
}