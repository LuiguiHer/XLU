package com.example.xlu.ui.home.model.api

import com.example.xlu.ui.home.model.BestMovies

data class ApiRespondBestMovies(
    val page:Int,
    val results:List<BestMovies>,
    val total_pages:Int,
    val total_results:Int
)