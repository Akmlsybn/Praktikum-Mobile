package com.example.movielistxml

import androidx.lifecycle.ViewModel

class MovieViewModelFactory(private val username: String) : androidx.lifecycle.ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MovieViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MovieViewModel(username) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}