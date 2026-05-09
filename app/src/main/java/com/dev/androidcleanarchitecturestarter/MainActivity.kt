package com.dev.androidcleanarchitecturestarter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dev.androidcleanarchitecturestarter.ui.DetailsScreen
import com.dev.androidcleanarchitecturestarter.ui.HomeScreen
import com.dev.androidcleanarchitecturestarter.ui.theme.AndroidCleanArchitectureStarterTheme
import com.dev.androidcleanarchitecturestarter.ui.viewmodels.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidCleanArchitectureStarterTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    AppNavigation()
                }
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") { backStackEntry ->
            val viewModel: WeatherViewModel = hiltViewModel(backStackEntry)
            HomeScreen(navController = navController, viewModel = viewModel)
        }

        composable(
            route = "details/{index}",
            arguments = listOf(navArgument("index") { type = NavType.IntType })
        ) { backStackEntry ->
            val index = backStackEntry.arguments?.getInt("index") ?: 0
            // Reach back to the Home entry to grab the same VM instance.
            val homeEntry = remember(backStackEntry) {
                navController.getBackStackEntry("home")
            }
            val viewModel: WeatherViewModel = hiltViewModel(homeEntry)
            DetailsScreen(
                navController = navController,
                index = index,
                viewModel = viewModel
            )
        }
    }
}
