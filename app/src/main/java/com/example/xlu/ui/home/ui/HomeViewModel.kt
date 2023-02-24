package com.example.xlu.ui.home.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.xlu.ui.home.model.BestMovies
import com.example.xlu.roomDB.repository.RepositoryMovieRoom
import com.example.xlu.ui.home.model.MovieSelected
import com.example.xlu.ui.home.model.Movies
import com.example.xlu.ui.home.model.Service.ApiServices
import com.example.xlu.ui.home.model.api.RetrofitConfig
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
@Inject constructor(
    private val repoRoom: RepositoryMovieRoom
    ) : ViewModel() {

    val urlImage = RetrofitConfig.URL_IMAGE

    private val _errorConnection = MutableLiveData<Boolean>()
    val errorConnection: LiveData<Boolean> = _errorConnection

    private val _movieSelected = MutableLiveData<MovieSelected>()
    val movieSelected: LiveData<MovieSelected> = _movieSelected

    private val _listToRoom = MutableLiveData<List<BestMovies>>()

    private val _listFromRoom = MutableLiveData<List<BestMovies>>()
    val listFromRoom: LiveData<List<BestMovies>> = _listFromRoom

    private val _movie = MutableLiveData<BestMovies>()
    val movie: LiveData<BestMovies> = _movie

    private val _stateToAddRoom =MutableLiveData<Boolean>()
    val stateToAddRoom : LiveData<Boolean> = _stateToAddRoom

    fun getBestMovies(){
        CoroutineScope(Dispatchers.IO).launch {
            repoRoom.getBestMoviesRoom().collect {
                _listFromRoom.postValue(it)
            }
        }
    }

    fun addBestMoviesToRoom(){
        val listMovies: List<BestMovies>? = _listToRoom.value
        CoroutineScope(Dispatchers.IO).launch {
            if (listMovies.isNullOrEmpty()){
                _errorConnection.postValue(true)
            }else{
                for (movie in listMovies){
                    repoRoom.addBestMovieRoom(movie)
                }
            }
        }
    }

    fun getMovies(){
        CoroutineScope(Dispatchers.IO).launch {
            val call = RetrofitConfig
                .getRetrofit.create(ApiServices::class.java)
                .getBestMovies("${RetrofitConfig.ENDPOINT_HOME}?api_key=${RetrofitConfig.API_KEY}&page=2")
            val movies = call.body()
            if (call.isSuccessful){
                _listToRoom.postValue(movies?.results as List<BestMovies>)
                _stateToAddRoom.postValue(true)
            }
        }
    }

    fun movieDetails(movie: BestMovies, navController: NavController) {
        val movieSelected = castMovie(movie)
        _movieSelected.postValue(movieSelected)
    }
    private fun castMovie(movie:BestMovies): MovieSelected{
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
