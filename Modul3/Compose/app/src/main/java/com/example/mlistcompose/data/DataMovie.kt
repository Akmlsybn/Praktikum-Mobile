package com.example.mlistcompose.data

import com.example.mlistcompose.R
import com.example.mlistcompose.model.ListMovie

class DataMovie{
    fun loadMovieList(): List<ListMovie> = listOf(
        ListMovie(R.string.title1, R.string.url1, R.drawable.ironman3, R.string.releaseDate1, R.string.desc1, R.string.description1),
        ListMovie(R.string.title2, R.string.url2, R.drawable.civilwar, R.string.releaseDate2, R.string.desc2, R.string.description2),
        ListMovie(R.string.title3, R.string.url3, R.drawable.infinitywar, R.string.releaseDate3, R.string.desc3, R.string.description3),
        ListMovie(R.string.title4, R.string.url4, R.drawable.endgame, R.string.releaseDate4, R.string.desc4, R.string.description4),
        ListMovie(R.string.title5, R.string.url5, R.drawable.nwh, R.string.releaseDate5, R.string.desc5, R.string.description5)
    )
}