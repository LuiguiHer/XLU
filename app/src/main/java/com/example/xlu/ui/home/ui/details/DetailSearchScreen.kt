package com.example.xlu.ui.home.ui.details

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.xlu.R
import com.example.xlu.ui.home.model.Movies
import com.example.xlu.ui.theme.Roboto

@Composable
fun DetailSearchScreen(movie: Movies, navController: NavController,viewModel: DetailSearchViewModel){
    Column(modifier = Modifier
        .fillMaxSize()) {
        ButtonBackDetailSearch(movie) {viewModel.onBackSelected(navController)}
        DetailsSearch(movie,viewModel)
    }
}
@Composable
fun DetailsSearch(movie:Movies, viewModel: DetailSearchViewModel){
    val modifier = Modifier
    val url = viewModel.urlImage
    Column(
        modifier
            .fillMaxSize()
            .padding(top = 6.dp)
        )
    {
        Column(modifier.padding(start = 40.dp)) {
            Text(
                text = movie.title,
                fontSize = 22.sp,
                fontFamily = Roboto,
                color = Color(0xFF1A1A1A)
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
        }
        AsyncImage(
            model = url + movie.backdrop_path,
            contentDescription = stringResource(id = R.string.image_poster),
            modifier.fillMaxWidth()
        )

        Spacer(modifier.padding(top = 10.dp))
        Row {
            AsyncImage(
                model = url + movie.poster_path,
                contentDescription = stringResource(id = R.string.image_poster),
                modifier.size(150.dp)
            )
            Text(
                text = movie.overview,
                fontSize = 15.sp,
                fontFamily = Roboto,
                modifier = Modifier.padding(end = 8.dp)
            )
        }
    }
}

@Composable
fun ButtonBackDetailSearch(movie:Movies,onBackSelected:()->Unit ) {

    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 6.dp), verticalAlignment = Alignment.CenterVertically)
    {
        Spacer(modifier = Modifier.padding(5.dp))
        OutlinedButton(onClick = { onBackSelected() },
            modifier = Modifier.size(50.dp),
            shape = RoundedCornerShape(5.dp),
            border = BorderStroke(0.dp, Color(0xFFFFFFFF))
        ) {
            Image(
                painter = painterResource(id = R.drawable.icon_back),
                contentDescription = stringResource(id = R.string.button_back)
            )
        }
        Spacer(modifier = Modifier.padding(25.dp))
        Text(
            text = movie.title,
            fontSize = 15.sp,
            fontFamily = Roboto,
            color = Color(0xFF3A3A3A)
        )
    }



}