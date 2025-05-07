package com.example.mlistcompose.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class ListMovie(
    @StringRes val title: Int,
    @StringRes val url: Int,
    @DrawableRes val image: Int,
    @StringRes val years: Int,
    @StringRes val desc: Int,
    @StringRes val description: Int
)