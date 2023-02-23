package com.example.xlu.navigation

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.xlu.MainActivity
import com.example.xlu.ui.login.ui.LoginScreen
import com.example.xlu.SplashScreen
import com.example.xlu.ui.home.ui.HomeScreen
import com.example.xlu.ui.home.ui.HomeViewModel
import com.example.xlu.ui.login.ui.LoginViewModel
import com.example.xlu.ui.sign_up.ui.SignUpViewModel
import com.example.xlu.ui.sign_up.ui.SingUpScreen

@Composable
fun AppNavigation(
    signUpViewModel: SignUpViewModel,
    loginViewModel: LoginViewModel,
    activity: MainActivity,
    homeViewModel: HomeViewModel,
    signInLauncher: ActivityResultLauncher<Intent>
){
    val navController = rememberNavController()
    NavHost(navController = navController ,
        startDestination = AppScreens.LoginScreen.route )
    {
        composable(route = AppScreens.SplashScreen.route){
            SplashScreen(navController)
        }
        composable(route= AppScreens.LoginScreen.route){
            LoginScreen(navController,loginViewModel,activity,signInLauncher)
        }
        composable(route= AppScreens.SignUpScreen.route){
            SingUpScreen(navController,signUpViewModel)
        }
        composable(route= AppScreens.HomeScreen.route){
            HomeScreen(homeViewModel,navController)
        }
    }
}