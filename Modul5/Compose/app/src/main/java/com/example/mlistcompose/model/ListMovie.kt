package com.example.mlistcompose.model

data class ListMovie(
    val id: Int,
    val title: String,
    val imageUrl: String?,
    val releaseDate: String?,
    val description: String?,
    val voteAverage: Double?
)