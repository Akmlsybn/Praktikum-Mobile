package com.example.movielistxml

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie (
    val title: String,
    val url: String,
    val image: Int,
    val plot: String,
    val years: String,
    val desc: String
): Parcelable