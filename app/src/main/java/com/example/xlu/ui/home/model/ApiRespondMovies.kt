package com.example.xlu.ui.home.model



data class ApiRespondMovies(
    val created_by:String,
    val description:String,
    val favorite_count:Int,
    val id:Int,
    val items:List<Movies>,
    val item_count:Int,
    val iso_639_1:String,
    val name:String,
    val poster_path:String
)