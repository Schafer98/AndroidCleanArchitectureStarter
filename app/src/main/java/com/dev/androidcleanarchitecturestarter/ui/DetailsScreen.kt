package com.dev.androidcleanarchitecturestarter.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.dev.androidcleanarchitecturestarter.domain.HourlyForecast
import com.dev.androidcleanarchitecturestarter.ui.viewmodels.WeatherUiState
import com.dev.androidcleanarchitecturestarter.ui.viewmodels.WeatherViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    navController: NavController,
    index: Int,
    viewModel: WeatherViewModel
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val forecast = (state as? WeatherUiState.Success)
        ?.weather?.hourlyForecast?.getOrNull(index)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Hour detail") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
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
            if (forecast == null) {
                Text(
                    "No data available — go back and refresh.",
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(24.dp)
                )
            } else {
                DetailsContent(forecast = forecast)
            }
        }
    }
}

@Composable
private fun DetailsContent(forecast: HourlyForecast) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            forecast.time,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        DetailRow(label = "Temperature", value = "${forecast.temperatureC} °C")
        DetailRow(label = "Precipitation chance", value = "${forecast.precipitationProbability} %")
        DetailRow(label = "Wind speed", value = "${forecast.windSpeedKmh} km/h")
    }
}

@Composable
private fun DetailRow(label: String, value: String) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = label,
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}
