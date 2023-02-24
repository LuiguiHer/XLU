package com.example.xlu.ui.home.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.xlu.ui.home.model.Movies
import com.example.xlu.ui.theme.Roboto
import com.example.xlu.R
import com.example.xlu.ui.home.model.MovieSelected
import com.example.xlu.ui.home.ui.details.DetailMovieScreen

@Composable
fun SearchScreen(viewModel: SearchViewModel){
    viewModel.getMovies()
    val movieSelected: MovieSelected by viewModel.movieSelected.observeAsState(initial = MovieSelected())
    if (movieSelected.title.isNotEmpty()){
        DetailMovieScreen(movieSelected, null, viewModel )
    }

    Column(modifier = Modifier.fillMaxSize()) {
        val search:String by viewModel.search.observeAsState(initial = "")
        SearchField(search){viewModel.onSearchChanged(it)}
        Spacer(modifier = Modifier.padding(bottom = 7.dp))
        LoadingListMovies(viewModel)
    }
    viewModel.addMovieToRoom()

}


@Composable
fun LoadingListMovies(viewModel: SearchViewModel){
    val movies:List<Movies> by viewModel.liveMoviesList.observeAsState(initial = emptyList())

    if (movies.isNotEmpty()){
        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 25.dp, vertical = 10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ){
            items(movies){movie ->
                ListItemsMovies(movie, viewModel)
            }
        }
    }
}
@Composable
fun ListItemsMovies(movie:Movies,viewModel: SearchViewModel){
    val urlImage:String = viewModel.urlImage
    Row(modifier = Modifier
        .fillMaxWidth()
        .clickable {
            viewModel.movieDetails(movie)
        }
    ) {

        AsyncImage(model = urlImage+movie.poster_path, contentDescription = null)
        Spacer(modifier = Modifier.padding(end = 10.dp))
        Column(modifier = Modifier.align(Alignment.Top)) {
            Spacer(modifier = Modifier.padding(top = 5.dp))
            Text(
                text = movie.title,
                fontFamily = Roboto,
                fontSize = 20.sp,
                fontStyle = FontStyle.Normal,
                color = Color(0xFF1B1B1B)
            )
            Spacer(modifier = Modifier.padding(top = 6.dp))
            movie.media_type?.let {
                Text(
                    text = it,
                    fontFamily = Roboto,
                    fontSize = 16.sp,
                    color = Color(0xFF6F6F6F)
                )
            }
            Spacer(modifier = Modifier.padding(top = 15.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = stringResource(id = R.string.icon_star),
                    tint = Color(0xFFFF8B10),

                )
                Spacer(modifier = Modifier.padding(start = 4.dp))
                Text(
                    text = movie.vote_average.toString(),
                    fontFamily = Roboto,
                    fontSize = 13.sp,
                    color = Color(0xFF1D1D1D)
                )
            }

        }
    }
}

@Composable
fun SearchField(search:String,onSearchChanged:(String) -> Unit) {
    OutlinedTextField(
        value = search, onValueChange = {onSearchChanged(it)},
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp, vertical = 15.dp),
        shape = RoundedCornerShape(40),
        placeholder = { Text(text = "") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        singleLine = true,
        maxLines = 1,
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color(0xFF090909),
            backgroundColor = Color(0xFFECECEC),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}
