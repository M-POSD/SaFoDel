package com.example.safodel.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.safodel.model.UserLocation

class LocationViewModel : ViewModel() {
    private var userLocation = MutableLiveData<UserLocation>()

    fun  getUserLocation(): LiveData<UserLocation> {
        return userLocation
    }

    fun setUserLocation(location: UserLocation) {
        userLocation.value = location
    }
}