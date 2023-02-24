package com.example.xlu.ui.home.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.xlu.roomDB.repository.RepositoryMovieRoom
import com.example.xlu.ui.home.model.BestMovies
import com.example.xlu.ui.home.model.MovieSelected
import com.example.xlu.ui.home.model.Service.ApiServices
import com.example.xlu.ui.home.model.Movies
import com.example.xlu.ui.home.model.api.RetrofitConfig
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel
    @Inject constructor(
        private val repoRoom: RepositoryMovieRoom
    )
    : ViewModel(){

    val urlImage:String = RetrofitConfig.URL_IMAGE

    private val _movieSelected = MutableLiveData<MovieSelected>()
    val movieSelected: LiveData<MovieSelected> = _movieSelected

    private val _liveMoviesList = MutableLiveData<List<Movies>>()
    val liveMoviesList: LiveData<List<Movies>> = _liveMoviesList

    private val _errorConnection =MutableLiveData<Boolean>()

    private val _search = MutableLiveData<String>()
    val search: LiveData<String> = _search

    private val _movie = MutableLiveData<Movies>()
    val movie: LiveData<Movies> = _movie

    fun addMovieToRoom() {
        val listMovie: List<Movies>? = _liveMoviesList.value
        CoroutineScope(Dispatchers.IO).launch {
            if (listMovie.isNullOrEmpty()){
                _errorConnection.postValue(true)
            }else {
                for (movie in listMovie){
                    repoRoom.addMovieToRoom(movie)
                }
            }
        }
    }

    fun getMovies(){
        CoroutineScope(Dispatchers.IO).launch {
            val call = RetrofitConfig
                .getRetrofit.create(ApiServices::class.java)
                .getMovies("${RetrofitConfig.ENDPOINT_SEARCH}?api_key=${RetrofitConfig.API_KEY}")
            val movies = call.body()
            if (call.isSuccessful){
                _liveMoviesList.postValue(movies?.items as List<Movies>)
            }
        }
    }

    fun onSearchChanged(search:String) {
        _search.value = search
    }

    fun movieDetails(movie:Movies){
        val movieSelected = castMovie(movie)
        _movieSelected.postValue(movieSelected)
    }

    private fun castMovie(movie: Movies): MovieSelected {
        val castMovie = MovieSelected()
        castMovie.adult = movie.adult
        castMovie.id_Movie = movie.id
        castMovie.backdrop_path = movie.backdrop_path
        castMovie.genre_ids = movie.genre_ids
        castMovie.media_type = movie.media_type
        castMovie.original_language = movie.original_language
        castMovie.original_title = movie.original_title
        castMovie.overview = movie.overview
        castMovie.popularity = movie.popularity
        castMovie.poster_path = movie.poster_path
        castMovie.release_date = movie.release_date
        castMovie.title = movie.title
        castMovie.video = movie.video
        castMovie.vote_average = movie.vote_average
        castMovie.vote_count = movie.vote_count
        return castMovie
    }

    fun backFromDetails(){
        _movieSelected.value = MovieSelected()
    }
}