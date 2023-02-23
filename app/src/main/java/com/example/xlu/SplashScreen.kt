package com.example.xlu

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.xlu.navigation.AppScreens
import com.example.xlu.ui.theme.colorBackground
import kotlinx.coroutines.delay


@Composable
fun SplashScreen(navController: NavController){
    LaunchedEffect(key1 = true ){
        delay(1000)
        navController.popBackStack()
        navController.navigate(AppScreens.LoginScreen.route)
    }
        Splash()
}

@Composable
fun Splash(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorBackground()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(

            painter = painterResource(id = R.drawable.logo_white),
            contentDescription = stringResource(id = R.string.description_logo_app),
            modifier = Modifier
                .size(dimensionResource(id = R.dimen.dp200))
            )
    }
}
