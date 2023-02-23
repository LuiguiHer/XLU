package com.example.xlu.ui.home.model

import com.example.xlu.roomDB.model.BestMovies

data class ApiRespondBestMovies(
    val page:Int,
    val results:List<BestMovies>,
    val total_pages:Int,
    val total_results:Int
)