package com.example.mlistcompose

import androidx.lifecycle.ViewModel
import com.example.mlistcompose.data.DataMovie
import com.example.mlistcompose.model.ListMovie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber

class MovieViewModel(someParam: String) : ViewModel() {
    private val _movies = MutableStateFlow<List<ListMovie>>(emptyList())
    val movies: StateFlow<List<ListMovie>> = _movies.asStateFlow()

    private val _navigateToUrl = MutableStateFlow<String?>(null)
    val navigateToUrl: StateFlow<String?> = _navigateToUrl.asStateFlow()

    private val _navigateToDetail = MutableStateFlow<ListMovie?>(null)
    val navigateToDetail: StateFlow<ListMovie?> = _navigateToDetail.asStateFlow()
    init {
        Timber.d("Loading movie list with param: $someParam")
        _movies.value = DataMovie().loadMovieList()
        Timber.d("Movie list loaded with ${_movies.value.size} items")
    }

    fun onLinkClicked(url: String) {
        Timber.d("URL clicked: $url")
        _navigateToUrl.value = url
    }

    fun onDetailClicked(movie: ListMovie) {
        Timber.d("Detail clicked for movie: ${movie.title}")
        _navigateToDetail.value = movie
    }

    fun onUrlNavigated() {
        _navigateToUrl.value = null
    }

    fun onDetailNavigated() {
        _navigateToDetail.value = null
    }
}

