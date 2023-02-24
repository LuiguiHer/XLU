package com.example.xlu.ui.home.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.xlu.R
import com.example.xlu.ui.theme.Roboto
import com.google.firebase.auth.FirebaseAuth

@Composable
fun AccountScreen(viewModel: AccountViewModel){
    val auth = FirebaseAuth.getInstance()
    val currentUser by remember { mutableStateOf(auth.currentUser) }
    val email = currentUser?.email ?: ""
    viewModel.getUser(email)
    val userName: String by viewModel.userName.observeAsState(initial = "")




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
        PhotoProfile(userName)
        ItemsRecords()
        Spacer(modifier = Modifier.padding(8.dp))
        DetailsProfile()
    }
}

@Composable
fun DetailsProfile(){
    Column(modifier = Modifier
        .background(Color(0xFFFFFFFF), shape = RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp))
        .fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
    )
    {

    }
}

@Composable
fun ItemsRecords(){
    Row(modifier = Modifier.width(250.dp), horizontalArrangement = Arrangement.SpaceBetween)
    {
        ItemRecord(title = stringResource(id = R.string.score), record = 0)
        ItemRecord(title = stringResource(id = R.string.lists), record = 0)
        ItemRecord(title = stringResource(id = R.string.favorite), record = 0)
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
fun PhotoProfile(userName: String){
    Column(modifier = Modifier
        .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.profile_xlu),
            contentDescription = stringResource(id = R.string.profile_image),
            modifier = Modifier.size(100.dp)
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