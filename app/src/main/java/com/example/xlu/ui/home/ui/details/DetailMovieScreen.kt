package com.example.xlu.ui.home.ui.details

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
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
    viewModel.getFavoriteMovie()
    val isMyFavorite: Boolean by viewModel.isMyFavorite.observeAsState(initial = false)
    val modifier = Modifier
    val url = RetrofitConfig.URL_IMAGE_HD
    Column(
        modifier
            .fillMaxSize())
    {
        Box(
            Modifier
                .padding(10.dp)
                .fillMaxSize()
                .clip(shape = RoundedCornerShape(40.dp))

        ) {
            AsyncImage(
                model = url + movie.poster_path,
                contentDescription = stringResource(id = R.string.image_poster),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(shape = RoundedCornerShape(20.dp))
            )
            Box(
                modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color(0xFFFFFF), Color(0xF2000000)),
                            startY = 0f,
                            endY = 1200f
                        )
                    )
            ) {
                Column(modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    DetailsMovie(movie, url)
                    AddFavorite(isMyFavorite,{viewModel.backFromDetails()},{viewModel.addToMyFavoriteMovie()},{viewModel.deleteMyFavoriteMovie()})
                }

            }
        }
    }
}

@Composable
fun DetailSearch(movie: MovieSelected, viewModel: SearchViewModel){
    viewModel.getFavoriteMovie()
    val isMyFavorite: Boolean by viewModel.isMyFavorite.observeAsState(initial = false)
    val modifier = Modifier
    val url = RetrofitConfig.URL_IMAGE_HD
    Column(
        modifier
            .fillMaxSize())
    {
        Box(
            Modifier
                .padding(10.dp)
                .fillMaxSize()
                .clip(shape = RoundedCornerShape(40.dp))

        ) {
            AsyncImage(
                model = url + movie.poster_path,
                contentDescription = stringResource(id = R.string.image_poster),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(shape = RoundedCornerShape(20.dp))
            )
            Box(
                modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color(0xFFFFFF), Color(0xF2000000)),
                            startY = 0f,
                            endY = 1200f
                        )
                    )
            ) {
                Column(modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    DetailsMovie(movie, url)
                    AddFavorite(isMyFavorite,{viewModel.backFromDetails()},{viewModel.addToMyFavoriteMovie()},{viewModel.deleteMyFavoriteMovie()})
                }

            }
        }
    }
}

@Composable
fun AddFavorite(isMyFavorite:Boolean,back:() -> Unit, addFavorite: () -> Unit, deleteFavorite: () -> Unit){
    if (isMyFavorite){
        ButtonFavorite(title = "My Favorite",Color(0xFFFF4D16),Color(0xFFFF0D0D), true){deleteFavorite()}
    }else{
        ButtonFavorite(title = "Add Favorite",Color(0xFFA0A0A0),Color(0XFFFFFFFF), false) {addFavorite()}
    }
    Spacer(modifier = Modifier.padding(15.dp))
    Column(
        modifier = Modifier
            .background(
                Color(0xD7FF5E1F),
                shape = RoundedCornerShape(10.dp)
            )
            .width(90.dp)
            .height(5.dp)
            .clickable { back() }
    ) {}
    Spacer(modifier = Modifier.padding(5.dp))
}

@Composable
fun ButtonFavorite(title:String,colorButton:Color,colorIcon:Color,isMyFavorite: Boolean,addFavorite: () -> Unit){
    var icon = painterResource(id = R.drawable.icon_add)
    if (isMyFavorite){
        icon = painterResource(id = R.drawable.icon_favorite)
    }
    Button(onClick = { addFavorite() },
        modifier = Modifier
            .height(40.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = colorButton
        ),
        shape = RoundedCornerShape(30.dp)
    ) {
        Text(
            text = title,
            fontSize = 15.sp,
            fontFamily = Roboto,
            color = Color(0xFFFFFFFF),
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.padding(3.dp))
        Icon(
            painter = icon,
            contentDescription = null,
            modifier = Modifier.size(25.dp),
            tint = colorIcon
        )
    }
}
@Composable
fun DetailsMovie(movie : MovieSelected, url : String){
    Column(
        modifier = Modifier.padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,

        )
    {
        Text(
            text = movie.title,
            fontSize = 30.sp,
            fontFamily = Roboto,
            color = Color(0xFFFF5100)
        )
        Text(
            text = movie.original_title+ stringResource(id = R.string.Original_Title),
            fontSize = 15.sp,
            fontFamily = Roboto,
            color = Color(0xFF7E7E7E)
        )
        Text(
            text = movie.release_date,
            fontSize = 10.sp,
            fontFamily = Roboto,
            color = Color(0xFF7E7E7E)
        )
    }
    Row(
        modifier = Modifier.padding(10.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center
        ) {
            AsyncImage(
                model = url + movie.poster_path,
                contentDescription = stringResource(id = R.string.image_poster),
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(25.dp))
                    .size(150.dp)
                    .fillMaxSize()
            )
        }
        Column(
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = movie.overview,
                fontSize = 15.sp,
                fontFamily = Roboto,
                modifier = Modifier.padding(end = 8.dp),
                color = Color(0xFFE7E7E7),
                maxLines = 9
            )
        }
    }
}