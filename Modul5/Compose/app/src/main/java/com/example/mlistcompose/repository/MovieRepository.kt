package com.example.mlistcompose.repository

import com.example.mlistcompose.data.local.MovieDao
import com.example.mlistcompose.data.local.MovieEntity
import com.example.mlistcompose.model.network.MovieApi
import com.example.mlistcompose.network.TmdbApiService
import com.example.mlistcompose.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.firstOrNull
import timber.log.Timber

class MovieRepository(
    private val apiService: TmdbApiService,
    private val movieDao: MovieDao
) {
    fun getPopularMovies(
        fetchFromRemote: Boolean
    ): Flow<Resource<List<MovieEntity>>> = flow {
        emit(Resource.Loading())

        val localMovies = movieDao.getAllMovies().firstOrNull()
        if (!localMovies.isNullOrEmpty()) {
            emit(Resource.Success(localMovies))
            Timber.d("Loaded ${localMovies.size} movies from cache.")
        }

        val shouldLoadFromApi = fetchFromRemote || localMovies.isNullOrEmpty()

        if (shouldLoadFromApi) {
            try {
                Timber.d("Fetching movies from API...")
                val response = apiService.getPopularMovies()
                val remoteMovies = response.results
                val movieEntities = remoteMovies.map { it.toMovieEntity() }

                movieDao.deleteAllMovies()
                movieDao.insertMovies(movieEntities)
                Timber.d("Fetched and cached ${movieEntities.size} movies from API.")

                val newLocalMovies = movieDao.getAllMovies().firstOrNull()
                if (newLocalMovies != null) {
                    emit(Resource.Success(newLocalMovies))
                } else {
                    emit(Resource.Error("Failed to retrieve new data from cache after API call."))
                }

            } catch (e: Exception) {
                Timber.e(e, "Error fetching movies from API: ${e.message}")
                if (localMovies.isNullOrEmpty()) {
                    emit(Resource.Error("Couldn't reach server. Check your internet connection.", emptyList()))
                } else {
                    emit(Resource.Error("Failed to update data. Displaying cached data.", localMovies))
                }
            }
        } else {
            if (localMovies.isNullOrEmpty()) {
                emit(Resource.Error("No cached data available and not fetching from remote."))
            }
        }
    }

    private fun MovieApi.toMovieEntity(): MovieEntity {
        return MovieEntity(
            id = this.id,
            title = this.title ?: "No Title",
            posterPath = this.posterPath,
            releaseDate = this.releaseDate,
            overview = this.overview,
            voteAverage = this.voteAverage
        )
    }
}