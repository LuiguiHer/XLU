package com.example.xlu.ui.home.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "bestMovies")
data class BestMovies(
    val adult: Boolean = false,
    val backdrop_path: String ="",
    val genre_ids: List<Int> = listOf(),
    @PrimaryKey val id: Int = 0,
    val media_type: String? = "",
    val original_language: String = "",
    val original_title: String = "",
    val overview: String = "",
    val popularity: Float = 0f,
    val poster_path: String ="",
    val release_date: String = "",
    val title: String = "",
    val video: Boolean = false,
    val vote_average: Float = 0f,
    val vote_count: Int = 0
)