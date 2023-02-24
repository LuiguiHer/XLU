package com.example.xlu.ui.home.ui.details

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import coil.compose.AsyncImage
import com.example.xlu.R
import com.example.xlu.ui.home.model.MovieSelected
import com.example.xlu.ui.home.model.api.RetrofitConfig
import com.example.xlu.ui.home.ui.HomeViewModel
import com.example.xlu.ui.home.ui.SearchViewModel
import com.example.xlu.ui.theme.Roboto

@Composable
fun DetailMovieScreen(movie: MovieSelected,homeViewModel: HomeViewModel?,searchViewModel: SearchViewModel?) {
    if (homeViewModel != null) {
        PopUpHome(movie, homeViewModel)
    } else {
        PopUpSearch(movie, searchViewModel!!)
    }
}

@Composable
fun PopUpHome(movie: MovieSelected, viewModel: HomeViewModel){
    Popup()
    {
        Box(modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xE4050505))
            .padding(bottom = 90.dp)
        )
        {
            Column(modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            )
            {
                Column(
                    modifier = Modifier
                        .clickable { viewModel.backFromDetails() }
                        .fillMaxWidth()
                        .height(70.dp)
                ){}
                DetailsHome(movie,viewModel)
            }
        }
    }
}
@Composable
fun PopUpSearch(movie: MovieSelected, viewModel: SearchViewModel){
    Popup()
    {
        Box(modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xE4050505))
            .padding(bottom = 90.dp)
        )
        {
            Column(modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            )
            {
                Column(
                    modifier = Modifier
                        .clickable { viewModel.backFromDetails() }
                        .fillMaxWidth()
                        .height(70.dp)
                ){}
                DetailSearch(movie,viewModel)
            }
        }
    }
}

@Composable
fun DetailsHome(movie: MovieSelected, viewModel: HomeViewModel){
    val modifier = Modifier
    val url = RetrofitConfig.URL_IMAGE_HD
    Column(
        modifier
            .fillMaxSize()
            .background(color = Color(0xFFFFFCFB), shape = RoundedCornerShape(40.dp)),
        horizontalAlignment = Alignment.CenterHorizontally
        )
    {
        AsyncImage(
            model = url + movie.backdrop_path,
            contentDescription = stringResource(id = R.string.image_poster),
            modifier
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp))
        )
        Spacer(modifier = Modifier.padding(top = 15.dp))
        Column(modifier.height(300.dp)) {
            Row {
                AsyncImage(
                    model = url + movie.poster_path,
                    contentDescription = stringResource(id = R.string.image_poster),
                    modifier.size(150.dp)
                )
                Column(modifier.padding(horizontal = 10.dp)) {
                    Text(
                        text = movie.title,
                        fontSize = 22.sp,
                        fontFamily = Roboto,
                        color = Color(0xFFD63C00)
                    )
                    Spacer(modifier.padding(1.dp))
                    Text(
                        text = movie.original_title+ stringResource(id = R.string.Original_Title),
                        fontSize = 15.sp,
                        fontFamily = Roboto,
                        color = Color(0xFF7E7E7E)
                    )
                    Spacer(modifier.padding(6.dp))
                    Text(
                        text = movie.release_date,
                        fontSize = 13.sp,
                        fontFamily = Roboto,
                        color = Color(0xFF7E7E7E)
                    )
                    Spacer(modifier.padding(10.dp))
                    Text(
                        text = movie.overview,
                        fontSize = 15.sp,
                        fontFamily = Roboto,
                        modifier = Modifier.padding(end = 8.dp),
                        color = Color(0xFF242424)
                    )
                }
            }
        }
        Column(modifier.padding(top = 60.dp)) {
            Box(
                modifier = Modifier
                    .background(
                        Color(0xFFF16F2B),
                        shape = RoundedCornerShape(10.dp)
                    )
                    .width(90.dp)
                    .height(5.dp)
                    .clickable { viewModel.backFromDetails() }
            ) {}
        }
    }
}

@Composable
fun DetailSearch(movie: MovieSelected, viewModel: SearchViewModel){
    val modifier = Modifier
    val url = RetrofitConfig.URL_IMAGE_HD
    Column(
        modifier
            .fillMaxSize()
            .background(color = Color(0xFFFFFCFB), shape = RoundedCornerShape(40.dp)),
        horizontalAlignment = Alignment.CenterHorizontally
        )
    {
        AsyncImage(
            model = url + movie.backdrop_path,
            contentDescription = stringResource(id = R.string.image_poster),
            modifier
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp))
        )
        Spacer(modifier = Modifier.padding(top = 15.dp))
        Column(modifier.height(300.dp)) {
            Row {
                AsyncImage(
                    model = url + movie.poster_path,
                    contentDescription = stringResource(id = R.string.image_poster),
                    modifier.size(150.dp)
                )
                Column(modifier.padding(horizontal = 10.dp)) {
                    Text(
                        text = movie.title,
                        fontSize = 22.sp,
                        fontFamily = Roboto,
                        color = Color(0xFFD63C00)
                    )
                    Spacer(modifier.padding(1.dp))
                    Text(
                        text = movie.original_title+ stringResource(id = R.string.Original_Title),
                        fontSize = 15.sp,
                        fontFamily = Roboto,
                        color = Color(0xFF7E7E7E)
                    )
                    Spacer(modifier.padding(6.dp))
                    Text(
                        text = movie.release_date,
                        fontSize = 13.sp,
                        fontFamily = Roboto,
                        color = Color(0xFF7E7E7E)
                    )
                    Spacer(modifier.padding(10.dp))
                    Text(
                        text = movie.overview,
                        fontSize = 15.sp,
                        fontFamily = Roboto,
                        modifier = Modifier.padding(end = 8.dp),
                        color = Color(0xFF242424)
                    )
                }
            }
        }
        Column(modifier.padding(top = 60.dp)) {
            Box(
                modifier = Modifier
                    .background(
                        Color(0xFFF16F2B),
                        shape = RoundedCornerShape(10.dp)
                    )
                    .width(90.dp)
                    .height(5.dp)
                    .clickable { viewModel.backFromDetails() }
            ) {

            }
        }
    }
}

