package com.example.movielistxml

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import timber.log.Timber

class MovieViewModel(private val username: String): ViewModel(){
    private val _movieList = MutableStateFlow<List<Movie>>(emptyList())
    val movieList: StateFlow<List<Movie>> get() = _movieList

    private val _selectedMovie = MutableStateFlow<Movie?>(null)
    val selectedMovie: StateFlow<Movie?> get() = _selectedMovie

    init {
        Timber.i("MovieViewModel created with username: $username")
    }

    fun setMovieList(movies: List<Movie>){
        Timber.i("Movie List loaded With: ${movies.size}")
        _movieList.value = movies
    }

    fun selectMovie(movie: Movie) {
        Timber.i("Movie selected: ${movie.title}")
        _selectedMovie.value = movie
    }
}