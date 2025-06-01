package com.example.mlistcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.mlistcompose.screen.MovieListScreen
import com.example.mlistcompose.ui.theme.MListComposeTheme
import com.example.mlistcompose.viewmodel.MovieViewModel
import com.example.mlistcompose.viewmodel.MovieViewModelFactory
import timber.log.Timber

class MainActivity : ComponentActivity() {

    private val viewModel: MovieViewModel by viewModels {
        MovieViewModelFactory("Test Debugging")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        Timber.plant(Timber.DebugTree())
        setContent {
            MListComposeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.Black
                ) {
                    MovieListScreen(viewModel = viewModel)
                }
            }
        }
    }
}
