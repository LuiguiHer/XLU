package com.example.xlu.roomDB.repository

import com.example.xlu.roomDB.model.BestMovies
import com.example.xlu.ui.home.model.Movies
import kotlinx.coroutines.flow.Flow


interface RepositoryMovieRoom {
    fun getAllFromRoom(): Flow<List<Movies>>
    fun addMovieToRoom(movie:Movies)

    fun getBestMoviesRoom(): Flow<List<BestMovies>>
    fun addBestMovieRoom(movie: BestMovies)

}