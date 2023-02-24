package com.example.xlu.roomDB.network

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.xlu.ui.home.model.BestMovies
import com.example.xlu.ui.home.model.Movies

@TypeConverters(Converters::class)
@Database(entities = [Movies::class, BestMovies::class], version = 1, exportSchema = false)
abstract class MoviesDB: RoomDatabase() {
    abstract  fun daoMovie(): DaoMovie
}