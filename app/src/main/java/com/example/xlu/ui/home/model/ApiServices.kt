package com.example.xlu.ui.home.model

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiServices {
    @GET
    suspend fun getBestMovies(@Url url:String): Response<ApiRespondBestMovies>

    @GET
    suspend fun getMovies(@Url url: String):Response<ApiRespondMovies>
}