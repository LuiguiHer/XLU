package com.example.xlu.ui.home.ui

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.xlu.R
import com.example.xlu.ui.home.model.BestMovies
import com.example.xlu.ui.home.model.MovieSelected
import com.example.xlu.ui.home.ui.details.DetailMovieScreen
import com.example.xlu.ui.theme.Roboto
import com.example.xlu.ui.utils.getCurrentUser
import com.google.firebase.auth.FirebaseAuth

@Composable
fun HomeScreen(viewModel: HomeViewModel) {
    viewModel.getMovies()
    val user = getCurrentUser()
    viewModel.getUser(user)

    val addToRoom: Boolean by viewModel.stateToAddRoom.observeAsState(initial = false)
    val errorConnection: Boolean by viewModel.errorConnection.observeAsState(initial = false)
    val movieSelected: MovieSelected by viewModel.movieSelected.observeAsState(initial = MovieSelected())
    if (movieSelected.title.isNotEmpty()){
        DetailMovieScreen(movieSelected, viewModel, null )
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFFFFFFFF))) {
        MovieTop(viewModel)

    }
    if (addToRoom){
        viewModel.addBestMoviesToRoom()
        viewModel.getBestMovies()
    }
    if (errorConnection){
        Toast.makeText(LocalContext.current, stringResource(id = R.string.connection_error),Toast.LENGTH_SHORT).show()
    }
}



@Composable
fun MovieTop(viewModel: HomeViewModel){
    Column(modifier = Modifier
        .fillMaxHeight()
        .fillMaxWidth())
    {
        ImageTopMovie()
        DetailsTopMovie()
        Divider(modifier = Modifier.padding(start = 20.dp, top = 6.dp, end = 20.dp, bottom = 0.dp),
            color = Color(0xFFE0E0E0), thickness = 1.dp)
        Spacer(modifier = Modifier.padding(8.dp))
        TopMoviesListTitle()
        Spacer(modifier = Modifier.padding(8.dp))
        LoadListBestMovies(viewModel)
    }
}

@Composable
fun DetailsTopMovie(){
    Column(modifier = Modifier.padding(start = 15.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = R.drawable.brightness),
                contentDescription = stringResource(id = R.string.icon_circle),
                tint = Color(0xFF59FF00),
                modifier = Modifier.size(10.dp)
            )
            Spacer(modifier = Modifier.padding(3.dp))
            Text(
                text = stringResource(id = R.string.new_movie_tittle),
                fontFamily = Roboto,
                fontWeight = FontWeight.Medium,
                fontSize = 25.sp,
                color = Color(0xFF3A3A3A)
            )
            Spacer(modifier = Modifier.padding(start = 90.dp))
            Icon(
                painter = painterResource(id = R.drawable.icon_play_circle),
                contentDescription = stringResource(id = R.string.icon_play),
                tint = Color(0xFFF82500),
                modifier = Modifier
                    .size(60.dp)
                    .padding(end = 25.dp)
            )
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(35.dp),
            modifier = Modifier.fillMaxWidth()
        )
        {
            Spacer(modifier = Modifier.padding(start = 5.dp))
            ItemTittleDescription(tittle = stringResource(id = R.string.season), description = "1")
            ItemTittleDescription(tittle = stringResource(id = R.string.chapters), description = "9")
            ItemTittleDescription(tittle = stringResource(id = R.string.score), description = "8.9")
        }
    }
}

@Composable
fun ImageTopMovie(){
    Box(contentAlignment = Alignment.TopStart
    ) {
        Image(
            painter = painterResource(id = R.drawable.the_last_poster),
            contentDescription = stringResource(id = R.string.image_poster),
            modifier = Modifier.clip(RoundedCornerShape(bottomStart = 25.dp, bottomEnd = 25.dp))
        )
        Column(modifier = Modifier.padding(top = 10.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .background(
                        color = Color(0x70020202),
                        shape = RoundedCornerShape(topEnd = 10.dp, bottomEnd = 10.dp)
                    )
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_add),
                    contentDescription = stringResource(id = R.string.icon_add),
                    tint = Color(0xFFFF2800)
                )
                Text(
                    text = stringResource(id = R.string.new_movie).uppercase(),
                    fontSize = 25.sp,
                    color = Color(0xFFFFFFFF),
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.padding(10.dp))
            }
        }

    }
}

@Composable
fun TopMoviesListTitle(){
    Row(modifier = Modifier
        .background(
            color = Color(0xCD020202),
            shape = RoundedCornerShape(topEnd = 10.dp, bottomEnd = 10.dp)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = Icons.Default.Star,
            contentDescription = stringResource(id = R.string.icon_star),
            tint = Color(0xFFFF2800)
        )
        Text(
            text = stringResource(id = R.string.top_movies).uppercase(),
            fontSize = 25.sp,
            color= Color(0xFFFFFFFF),
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.padding(10.dp))
    }
}

@Composable
fun ItemTittleDescription(tittle:String, description: String){
    Column(horizontalAlignment = Alignment.CenterHorizontally)
    {
        Text(text = description,
            fontSize = 15.sp,
            color = Color(0xFF7A7A7A),
            fontFamily = Roboto,
        )
        Text(text = tittle,
            fontSize = 14.sp,
            color = Color(0xFF7A7A7A),
            fontFamily = Roboto
        )

    }
}

@Composable
fun LoadListBestMovies(viewModel: HomeViewModel){
    val movies:List<BestMovies> by viewModel.listFromRoom.observeAsState(initial = emptyList())


    if (movies.isNotEmpty()){
        Column(modifier = Modifier.padding(start = 15.dp)) {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                items(movies)
                { movie ->
                    ListItemsBestMovies(movie, viewModel)
                }
            }
        }
    }
}

@Composable
fun ListItemsBestMovies(movie: BestMovies, viewModel: HomeViewModel){
    val urlImage:String = viewModel.urlImage

    Column(modifier = Modifier.fillMaxHeight()) {
        Column(modifier = Modifier
            .width(100.dp)
            .clickable { viewModel.movieDetails(movie) }
        ) {
            AsyncImage(
                model = urlImage + movie.poster_path,
                contentDescription = stringResource(id = R.string.image_poster),
                modifier = Modifier
                    .size(150.dp)
                    .clip(shape = RoundedCornerShape(15.dp))
            )
            Spacer(modifier = Modifier.padding(top = 5.dp))

            Row(verticalAlignment = Alignment.CenterVertically){
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = null,
                    tint = Color(0xFFFF8B10),
                    modifier = Modifier.size(15.dp)
                )
                Spacer(modifier = Modifier.padding(start = 4.dp))
                Text(
                    text = movie.vote_average.toString(),
                    fontFamily = Roboto,
                    fontSize = 13.sp,
                    color = Color(0xFF414141)
                )
            }
            Column(modifier = Modifier.width(100.dp))
            {
                Text(
                    text = movie.title,
                    fontFamily = Roboto,
                    fontSize = 16.sp,
                    color = Color(0xFF3A3A3A),
                    maxLines = 2,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}
