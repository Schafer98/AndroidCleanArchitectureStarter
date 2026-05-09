package com.dev.androidcleanarchitecturestarter.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.dev.androidcleanarchitecturestarter.domain.HourlyForecast
import com.dev.androidcleanarchitecturestarter.domain.Weather
import com.dev.androidcleanarchitecturestarter.ui.viewmodels.WeatherUiState
import com.dev.androidcleanarchitecturestarter.ui.viewmodels.WeatherViewModel

// Berlin. Replace with a city picker or device-location flow later.
private const val DEFAULT_LAT = 52.52
private const val DEFAULT_LON = 13.41

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: WeatherViewModel
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    // Fire the first load exactly once, when state is still Idle.
    LaunchedEffect(Unit) {
        if (state is WeatherUiState.Idle) {
            viewModel.loadWeather(DEFAULT_LAT, DEFAULT_LON)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Weather") },
                actions = {
                    IconButton(onClick = { viewModel.loadWeather(DEFAULT_LAT, DEFAULT_LON) }) {
                        Icon(Icons.Default.Refresh, contentDescription = "Refresh")
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when (val s = state) {
                WeatherUiState.Idle,
                WeatherUiState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                is WeatherUiState.Success -> SuccessContent(
                    weather = s.weather,
                    onItemClick = { index -> navController.navigate("details/$index") }
                )
                is WeatherUiState.Error -> ErrorContent(
                    message = s.message,
                    onRetry = { viewModel.loadWeather(DEFAULT_LAT, DEFAULT_LON) },
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

@Composable
private fun SuccessContent(
    weather: Weather,
    onItemClick: (Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item { LocationHeader(weather) }
        itemsIndexed(weather.hourlyForecast) { index, forecast ->
            HourlyRow(forecast = forecast, onClick = { onItemClick(index) })
        }
    }
}

@Composable
private fun LocationHeader(weather: Weather) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(weather.timezone, style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(4.dp))
            Text(
                "Lat ${weather.coordinates.latitude}, Lon ${weather.coordinates.longitude}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                "Elevation ${weather.elevation} m",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
private fun HourlyRow(
    forecast: HourlyForecast,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(forecast.time, style = MaterialTheme.typography.titleMedium)
                Text(
                    "${forecast.precipitationProbability}% rain  ·  ${forecast.windSpeedKmh} km/h",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Text(
                "${forecast.temperatureC}°C",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
private fun ErrorContent(
    message: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Couldn't load weather", style = MaterialTheme.typography.titleMedium)
        Text(message, style = MaterialTheme.typography.bodyMedium)
        Button(onClick = onRetry) { Text("Retry") }
    }
}
