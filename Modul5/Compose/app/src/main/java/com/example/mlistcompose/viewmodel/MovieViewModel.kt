package com.example.mlistcompose.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mlistcompose.data.preferences.SettingsManager
import com.example.mlistcompose.model.ListMovie
import com.example.mlistcompose.repository.MovieRepository
import com.example.mlistcompose.utils.Constants
import com.example.mlistcompose.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber

class MovieViewModel(
    private val repository: MovieRepository,
    private val settingsManager: SettingsManager
) : ViewModel() {
    private val _movies = MutableStateFlow<Resource<List<ListMovie>>>(Resource.Loading())
    val movies: StateFlow<Resource<List<ListMovie>>> = _movies.asStateFlow()

    private val _navigateToUrl = MutableStateFlow<String?>(null)
    val navigateToUrl: StateFlow<String?> = _navigateToUrl.asStateFlow()

    private val _navigateToDetail = MutableStateFlow<ListMovie?>(null)
    val navigateToDetail: StateFlow<ListMovie?> = _navigateToDetail.asStateFlow()

    val isDarkModeEnabled: StateFlow<Boolean> = settingsManager.getDarkModeEnabledAsFlow()
        .distinctUntilChanged()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = settingsManager.isDarkModeEnabled()
        )

    init {
        fetchMovies()
    }

    fun fetchMovies(fetchFromRemote: Boolean = true) {
        viewModelScope.launch {
            repository.getPopularMovies(fetchFromRemote).collect { result ->
                _movies.value = when (result) {
                    is Resource.Success -> {
                        val uiMovies = result.data?.map { movieEntity ->
                            ListMovie(
                                id = movieEntity.id,
                                title = movieEntity.title,
                                imageUrl = if (!movieEntity.posterPath.isNullOrEmpty()) Constants.TMDB_IMAGE_BASE_URL + movieEntity.posterPath else null,
                                releaseDate = movieEntity.releaseDate,
                                description = movieEntity.overview,
                                voteAverage = movieEntity.voteAverage
                            )
                        } ?: emptyList()
                        Resource.Success(uiMovies)
                    }
                    is Resource.Error -> Resource.Error(result.message ?: "An unexpected error occurred", result.data?.map { movieEntity ->
                        ListMovie(
                            id = movieEntity.id,
                            title = movieEntity.title,
                            imageUrl = if (!movieEntity.posterPath.isNullOrEmpty()) Constants.TMDB_IMAGE_BASE_URL + movieEntity.posterPath else null,
                            releaseDate = movieEntity.releaseDate,
                            description = movieEntity.overview,
                            voteAverage = movieEntity.voteAverage
                        )
                    } ?: emptyList())
                    is Resource.Loading -> Resource.Loading()
                }
            }
        }
    }

    fun setDarkMode(isEnabled: Boolean) {
        settingsManager.setDarkModeEnabled(isEnabled)
    }

    fun onImdbClicked(url: String) {
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