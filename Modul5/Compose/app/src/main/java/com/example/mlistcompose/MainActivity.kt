package com.example.mlistcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mlistcompose.screen.MovieListScreen
import com.example.mlistcompose.screen.SettingsScreen
import com.example.mlistcompose.viewmodel.MovieViewModel
import com.example.mlistcompose.data.preferences.SettingsManager
import com.example.mlistcompose.ui.theme.MListComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel: MovieViewModel by viewModels {
            (application as MListComposeApplication).movieViewModelFactory
        }

        val settingsManager = SettingsManager(this)

        setContent {
            val isDarkModeEnabled by viewModel.isDarkModeEnabled.collectAsState(initial = settingsManager.isDarkModeEnabled())

            MListComposeTheme(darkTheme = isDarkModeEnabled) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = "movieList"
                    ) {
                        composable("movieList") {
                            MovieListScreen(
                                viewModel = viewModel,
                                navController = navController
                            )
                        }
                        composable("settings") {
                            SettingsScreen(navController = navController, viewModel = viewModel)
                        }
                    }
                }
            }
        }
    }
}