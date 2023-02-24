package com.example.xlu.ui.home.model.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitConfig {
    companion object{
        val getRetrofit = retrofitBuild()
        const val URL_IMAGE = "https://image.tmdb.org/t/p/w200"
        const val URL_IMAGE_HD = "https://image.tmdb.org/t/p/w500"
        const val ENDPOINT_HOME = "movie/top_rated"
        const val ENDPOINT_SEARCH = "list/1"
        const val API_KEY = "c5c47722a4adcc77f6e84f28a48b857a"
        const val ENDPOINT_RESULT = "search/movie"


        private fun retrofitBuild(): Retrofit {
            return Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }


}