package com.example.xlu.ui.home.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.xlu.roomDB.repository.RepositoryMovieRoom
import com.example.xlu.ui.home.bottomNavBar.BottomBarScreens
import com.example.xlu.ui.home.model.ApiServices
import com.example.xlu.ui.home.model.Movies
import com.example.xlu.ui.home.model.RetrofitConfig
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

    fun movieDetails(movie:Movies,navController: NavController){
        _movie.value= movie
        navController.navigate(BottomBarScreens.DetailSearch.route)

    }

}