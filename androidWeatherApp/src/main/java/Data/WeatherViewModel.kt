package com.example.pignol_lafarge.android

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class WeatherViewModel(private val repository: WeatherRepository = WeatherRepository()): ViewModel() {

    private val _weatherState = MutableLiveData<WeatherResponse?>()
    val weatherState: LiveData<WeatherResponse?> = _weatherState

    fun getWeatherForCity(cityName: String) {
        viewModelScope.launch {
            val response = repository.getWeather(cityName, "7515a0aee27e9f71154316a53da362ba")
            if (response.isSuccessful) {
                _weatherState.postValue(response.body())
            } else {
                // Handle errors appropriately
                _weatherState.postValue(null)
            }
        }
    }
}
