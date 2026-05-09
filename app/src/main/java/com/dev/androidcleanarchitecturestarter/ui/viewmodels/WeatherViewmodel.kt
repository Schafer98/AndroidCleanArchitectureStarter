package com.dev.androidcleanarchitecturestarter.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.androidcleanarchitecturestarter.domain.Weather
import com.dev.androidcleanarchitecturestarter.domain.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface WeatherUiState {
    /** Initial state before any load is requested. Lets the UI fire a single fetch. */
    data object Idle : WeatherUiState
    data object Loading : WeatherUiState
    data class Success(val weather: Weather) : WeatherUiState
    data class Error(val message: String) : WeatherUiState
}

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository
) : ViewModel() {

    private val _state = MutableStateFlow<WeatherUiState>(WeatherUiState.Idle)
    val state: StateFlow<WeatherUiState> = _state.asStateFlow()

    fun loadWeather(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            _state.value = WeatherUiState.Loading
            repository.getForecast(latitude, longitude)
                .onSuccess { _state.value = WeatherUiState.Success(it) }
                .onFailure {
                    _state.value = WeatherUiState.Error(it.message ?: "Couldn't load weather")
                }
        }
    }
}
