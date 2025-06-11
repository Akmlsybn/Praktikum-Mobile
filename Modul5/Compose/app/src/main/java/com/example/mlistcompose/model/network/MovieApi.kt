package com.example.mlistcompose.model.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieApi(
    val id: Int,
    val title: String?,
    @SerialName("poster_path") val posterPath: String?,
    @SerialName("release_date") val releaseDate: String?,
    val overview: String?,
    @SerialName("vote_average") val voteAverage: Double?
)