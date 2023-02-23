package com.example.xlu.ui.login.ui

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.util.Patterns
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.xlu.R
import com.example.xlu.navigation.AppScreens
import com.example.xlu.ui.sign_up.data.repository.UserRepositoryFirebase
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel
@Inject constructor(
    private val userRepository: UserRepositoryFirebase
) : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private val docRef = db.collection("Users")
    val isUserSignedIn: LiveData<Boolean>
        get() = _isUserSignedIn
    private val _isUserSignedIn = MutableLiveData<Boolean>()

    init {
        _isUserSignedIn.value = auth.currentUser != null
    }

    private val tokenNewDevice = MutableLiveData<String>()
    private val ioScope = CoroutineScope(Dispatchers.IO)
    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email
    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password
    private val _loginEnable = MutableLiveData<Boolean>()
    val loginEnable: LiveData<Boolean> = _loginEnable
    private val _isErrorPassword = MutableLiveData<Boolean>()
    val isErrorPassword: LiveData<Boolean> = _isErrorPassword
    private val _isErrorEmail = MutableLiveData<Boolean>()
    val isErrorEmail: LiveData<Boolean> = _isErrorEmail
    private val _notAccessByGoogle = MutableLiveData<Boolean>()
    val notAccessByGoogle: LiveData<Boolean> = _notAccessByGoogle

    //functions
    fun onLoginChanged(email: String, password: String) {
        _email.value = email
        _password.value = password
        _isErrorEmail.value = false
        _isErrorPassword.value = false
        _loginEnable.value = isValidEmail(email) && isValidPassword(password)
    }

    private fun isValidEmail(email: String): Boolean =
        Patterns.EMAIL_ADDRESS.matcher(email).matches()

    private fun isValidPassword(password: String): Boolean = password.length > 6

    fun onLoginSelected(navController: NavController) {
        val email = email.value.toString()
        val password = password.value.toString()
        getUSer(email, password, navController)
    }

    fun tokenDevice() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            tokenNewDevice.postValue(task.result)
        }
    }

    fun onSignUp(navController: NavController) {
        navController.navigate(AppScreens.SignUpScreen.route)
    }

    private fun accessUserAuth(email: String, password: String, navController: NavController) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                navController.popBackStack()
            } else
                _isErrorPassword.value = true

        }
    }

    private fun getUSer(email: String, password: String, navController: NavController) {
        val newToken = tokenNewDevice.value.toString()
        ioScope.launch {
            val data = userRepository.getUser(email)
            data.get().addOnSuccessListener { user ->
                if (user.exists()) {
                    docRef.document(email).update("tokenDevice", newToken)
                    accessUserAuth(email, password, navController)
                } else
                    _isErrorEmail.value = true
            }
        }
    }

    fun signInWithGoogle(activity: Activity, signInLauncher: ActivityResultLauncher<Intent>) {
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(activity.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        val googleSignInClient = GoogleSignIn.getClient(activity, googleSignInOptions)
        signInLauncher.launch(googleSignInClient.signInIntent)
    }

    fun handleSignInResult(data: Intent?) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        try {
            val account = task.getResult(ApiException::class.java)
            val credential = GoogleAuthProvider.getCredential(account?.idToken, null)
            auth.signInWithCredential(credential)
                .addOnCompleteListener {
                    _isUserSignedIn.value = it.isSuccessful
                }
        } catch (e: ApiException) {
            _notAccessByGoogle.value = true
            Log.w(TAG, "signInResult:failed code=${e.statusCode}", e)
        }
    }

    companion object {
        private const val TAG = "AuthViewModel"
    }
}