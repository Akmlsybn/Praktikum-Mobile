package com.example.mlistcompose

import android.app.Application
import com.example.mlistcompose.data.local.AppDatabase
import com.example.mlistcompose.network.RetrofitClient
import com.example.mlistcompose.repository.MovieRepository
import timber.log.Timber
import com.example.mlistcompose.viewmodel.MovieViewModelFactory
import com.example.mlistcompose.data.preferences.SettingsManager

class MListComposeApplication : Application() {

    private val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }
    private val movieDao by lazy { database.movieDao() }
    private val tmdbApiService by lazy { RetrofitClient.tmdbApiService }
    private val settingsManager: SettingsManager by lazy { SettingsManager(this) }

    val movieRepository: MovieRepository by lazy {
        MovieRepository(tmdbApiService, movieDao)
    }

    val movieViewModelFactory: MovieViewModelFactory by lazy {
        MovieViewModelFactory(movieRepository, settingsManager)
    }

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}