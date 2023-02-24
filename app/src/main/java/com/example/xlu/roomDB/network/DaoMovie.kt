package com.example.xlu.roomDB.network

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.xlu.ui.home.model.BestMovies
import com.example.xlu.ui.home.model.Movies
import kotlinx.coroutines.flow.Flow

@Dao
interface DaoMovie {
    @Query("SELECT * FROM movies")
    fun getAll(): Flow<List<Movies>>

    @Query("SELECT * FROM bestMovies")
    fun getBestMovies(): Flow<List<BestMovies>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addMovie(movie: Movies)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addBestMovie(movie: BestMovies)
}