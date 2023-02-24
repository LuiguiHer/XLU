package com.example.xlu.roomDB.repository

import com.example.xlu.ui.home.model.BestMovies
import com.example.xlu.roomDB.network.DaoMovie
import com.example.xlu.ui.home.model.Movies
import kotlinx.coroutines.flow.Flow

class RepositoryMovieImpl(
    private val daoMovie: DaoMovie
): RepositoryMovieRoom {
    override fun getAllFromRoom(): Flow<List<Movies>> = daoMovie.getAll()
    override fun addMovieToRoom(movie: Movies) = daoMovie.addMovie(movie)
    override fun getBestMoviesRoom(): Flow<List<BestMovies>> = daoMovie.getBestMovies()
    override fun addBestMovieRoom(movie: BestMovies) = daoMovie.addBestMovie(movie)

}