package com.example.xlu.ui.home.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "movies")
data class Movies(
    var adult: Boolean = false,
    var backdrop_path: String = "",
    var genre_ids: List<Int> = listOf(),
    @PrimaryKey var id: Int = 0,
    var media_type: String? = "",
    var original_language: String = "",
    var original_title: String = "",
    var overview: String = "",
    var popularity: Float = 0f,
    var poster_path: String = "",
    var release_date: String = "",
    var title: String = "",
    var video: Boolean = false,
    var vote_average: Float = 0f,
    var vote_count: Int = 0
)