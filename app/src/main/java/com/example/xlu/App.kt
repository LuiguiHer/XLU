package com.example.xlu

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.LiveData
import com.example.xlu.navigation.AppNavigation
import com.example.xlu.ui.home.bottomNavBar.BottomNavBar
import com.example.xlu.ui.home.ui.AccountViewModel
import com.example.xlu.ui.home.ui.HomeViewModel
import com.example.xlu.ui.home.ui.SearchViewModel
import com.example.xlu.ui.login.ui.LoginViewModel
import com.example.xlu.ui.sign_up.ui.SignUpViewModel
import com.google.firebase.auth.FirebaseUser

@Composable
fun App(
    currentUserLiveData: LiveData<FirebaseUser>,
    loginViewModel: LoginViewModel,
    signUpViewModel: SignUpViewModel,
    homeViewModel: HomeViewModel,
    searchViewModel: SearchViewModel,
    accountViewModel: AccountViewModel,
    mainActivity: MainActivity,
    signInLauncher: ActivityResultLauncher<Intent>,
){
    val currentUser by currentUserLiveData.observeAsState()

    if (currentUser != null) {
        BottomNavBar(
            searchViewModel,
            homeViewModel,
            accountViewModel,
            mainActivity
        )
    } else {
        AppNavigation(signUpViewModel, loginViewModel, mainActivity, homeViewModel,signInLauncher)
    }


}
