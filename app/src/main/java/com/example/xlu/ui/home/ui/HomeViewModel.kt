package com.example.xlu.ui.home.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.xlu.roomDB.model.BestMovies
import com.example.xlu.roomDB.repository.RepositoryMovieRoom
import com.example.xlu.ui.home.bottomNavBar.BottomBarScreens
import com.example.xlu.ui.home.model.ApiServices
import com.example.xlu.ui.home.model.RetrofitConfig
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

    fun movieDetails(movie: BestMovies,navController: NavController) {
        _movie.value = movie
        navController.navigate(BottomBarScreens.DetailHome.route)
    }

}
