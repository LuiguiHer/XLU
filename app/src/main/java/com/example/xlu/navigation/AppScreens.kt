package com.example.xlu.navigation

sealed class AppScreens(val route: String){
    object SplashScreen: AppScreens("splash_screen")
    object LoginScreen: AppScreens("login_screen")
    object SignUpScreen: AppScreens("sign_up_screen")
    object HomeScreen: AppScreens("home_screen")
}
