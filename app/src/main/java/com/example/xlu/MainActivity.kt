package com.example.xlu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.MutableLiveData
import com.example.xlu.ui.home.ui.AccountViewModel
import com.example.xlu.ui.home.ui.HomeViewModel
import com.example.xlu.ui.home.ui.SearchViewModel
import com.example.xlu.ui.login.ui.LoginViewModel
import com.example.xlu.ui.sign_up.ui.SignUpViewModel
import com.example.xlu.ui.theme.XLUTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var currentUserLiveData: MutableLiveData<FirebaseUser>
    private val signUpViewModel: SignUpViewModel by viewModels()
    private val loginViewModel: LoginViewModel by viewModels()
    private val accountViewModel: AccountViewModel by viewModels()
    private val homeViewModel: HomeViewModel by viewModels()
    private val searchViewModel: SearchViewModel by viewModels()

    private val signInLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        loginViewModel.handleSignInResult(result.data)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        currentUserLiveData = MutableLiveData(auth.currentUser)
        val authStateListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            currentUserLiveData.value = firebaseAuth.currentUser
        }
        auth.addAuthStateListener(authStateListener)

        setContent {
            XLUTheme {
                App(currentUserLiveData,
                    loginViewModel,
                    signUpViewModel,
                    homeViewModel,
                    searchViewModel,
                    accountViewModel,
                    this,
                    signInLauncher
                )
            }
        }
    }
}




