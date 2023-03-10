package com.example.xlu.ui.home.ui

import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.xlu.R
import com.example.xlu.ui.home.model.Movies
import com.example.xlu.ui.sign_up.model.UserEntity
import com.example.xlu.ui.theme.Roboto
import com.example.xlu.ui.utils.getCurrentUser
import com.google.firebase.auth.FirebaseAuth

@Composable
fun AccountScreen(viewModel: AccountViewModel,context: Context){
    val email = getCurrentUser()
    viewModel.getUser(email)
    val urlImage = viewModel.retrofit.URL_IMAGE_HD
    val user by viewModel.remoteUser.observeAsState(initial = UserEntity())
    val listFavorite: List<Movies> by viewModel.listFavoriteMovies.observeAsState(initial = emptyList())
    val countFavorite: Int by viewModel.countMovies.observeAsState(initial = 0)



    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFF1B1B1B)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally )
    {

        Spacer(modifier = Modifier.padding(4.dp))
        Icon(painter = painterResource(id = R.drawable.icon_logout),
            contentDescription = null,
            tint = Color(0XFFFFFFFF),
            modifier = Modifier
                .align(Alignment.End)
                .padding(end = 10.dp)
                .clickable { viewModel.closedAuthUser() }
        )
        PhotoProfile(user.name,context,viewModel,user.urlProfile)
        ItemsRecords(countFavorite)
        Spacer(modifier = Modifier.padding(8.dp))
        DetailsProfile(urlImage,listFavorite)
    }
}

@Composable
fun DetailsProfile(url:String,movies: List<Movies>){
    Column(modifier = Modifier
        .background(Color(0xFFFFFFFF), shape = RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp))
        .fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Column(modifier = Modifier.padding(10.dp)) {
            Text(text = "Favorite Movies", fontSize = 20.sp)
            Spacer(modifier = Modifier.padding(10.dp))
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                items(movies)
                { movie ->
                    ListItem(url,movie)
                }
            }
        }
    }
}

@Composable
fun ListItem(url: String,movie: Movies){
    Column(modifier = Modifier.fillMaxHeight()) {
        Column(modifier = Modifier
            .width(100.dp)
            .clickable { }
        ) {
            AsyncImage(
                model = url + movie.poster_path,
                contentDescription = stringResource(id = R.string.image_poster),
                modifier = Modifier
                    .size(150.dp)
                    .clip(shape = RoundedCornerShape(15.dp))
            )
        }
    }
}

@Composable
fun ItemsRecords(favorite: Int){
    Row(modifier = Modifier.width(250.dp), horizontalArrangement = Arrangement.SpaceBetween)
    {
        ItemRecord(title = stringResource(id = R.string.score), record = 0)
        ItemRecord(title = stringResource(id = R.string.lists), record = 0)
        ItemRecord(title = stringResource(id = R.string.favorite), record = favorite)
    }
}

@Composable
fun ItemRecord(title: String, record:Int){
    Column(horizontalAlignment = Alignment.CenterHorizontally) 
    {
        Text(text = record.toString(),
            fontSize = 25.sp,
            color = Color(0xFFFF460E),
            fontFamily = Roboto,
            fontWeight = FontWeight.Bold
        )

        Text(text = title,
            fontSize = 12.sp,
            color = Color(0xFFA7A7A7),
            fontFamily = Roboto,
        )
    }
}

@Composable
fun PhotoProfile(userName: String,context: Context,viewModel: AccountViewModel,url:String){
    val pickImage = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri !=  null){
            viewModel.upLoadImageProfile(context,uri)
        }
    }

    Column(modifier = Modifier
        .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = url,
            contentDescription = null,
            modifier = Modifier
                .size(150.dp)
                .clip(shape = RoundedCornerShape(75.dp))
                .clickable { pickImage.launch("image/*") }
        )
        Spacer(modifier = Modifier.padding(4.dp))
        Text(
            text = userName,
            fontWeight = FontWeight.Medium,
            fontFamily = Roboto,
            fontSize = 22.sp,
            color = Color(0xFFFFFFFF),
        )
    }
}


