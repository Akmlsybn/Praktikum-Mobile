package com.example.mlistcompose.data

import com.example.mlistcompose.R
import com.example.mlistcompose.model.ListMovie

class DataMovie{
    fun loadMovieList(): List<ListMovie> = listOf(
        ListMovie(R.string.title1, "https://www.imdb.com/title/tt1300854/?ref_=nv_sr_srsg_0_tt_8_nm_0_in_0_q_Iron%2520Man%25203" , R.drawable.ironman3, R.string.releaseDate1, R.string.desc1, R.string.description1),
        ListMovie(R.string.title2, "https://www.imdb.com/title/tt3498820/?ref_=nv_sr_srsg_0_tt_6_nm_0_in_0_q_captain%2520civil%2520war", R.drawable.civilwar, R.string.releaseDate2, R.string.desc2, R.string.description2),
        ListMovie(R.string.title3, "https://www.imdb.com/title/tt4154756/?ref_=nv_sr_srsg_0_tt_8_nm_0_in_0_q_infinity%2520war", R.drawable.infinitywar, R.string.releaseDate3, R.string.desc3, R.string.description3),
        ListMovie(R.string.title4, "https://www.imdb.com/title/tt4154796/?ref_=nv_sr_srsg_0_tt_8_nm_0_in_0_q_endgame", R.drawable.endgame, R.string.releaseDate4, R.string.desc4, R.string.description4),
        ListMovie(R.string.title5, "https://www.imdb.com/title/tt10872600/?ref_=nv_sr_srsg_0_tt_8_nm_0_in_0_q_no%2520way", R.drawable.nwh, R.string.releaseDate5, R.string.desc5, R.string.description5)
    )
}