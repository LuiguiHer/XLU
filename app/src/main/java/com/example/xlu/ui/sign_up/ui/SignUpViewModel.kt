package com.example.xlu.ui.sign_up.ui

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.xlu.navigation.AppScreens
import com.example.xlu.ui.sign_up.data.repository.UserRepositoryFirebase
import com.example.xlu.ui.sign_up.model.UserEntity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject


@HiltViewModel
class SignUpViewModel
@Inject constructor(
    private val userRepository: UserRepositoryFirebase
) : ViewModel() {

    private val ioScope = CoroutineScope(Dispatchers.IO)
    private val auth = FirebaseAuth.getInstance()
    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email
    private val tokenDevice = MutableLiveData<String>()
    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password
    private val _name = MutableLiveData<String>()
    val name: LiveData<String> = _name
    private val _signUpEnable = MutableLiveData<Boolean>()
    val signUpEnable: LiveData<Boolean> = _signUpEnable
    private val _userRegistered = MutableLiveData<Boolean>()
    private val _userNotRegistered = MutableLiveData<Boolean>()
    private val liveEmailFound = MutableLiveData<Boolean>()
    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> = _isError


    //Functions
    private fun isValidPassword(password: String): Boolean = password.length > 8
    private fun isValidName(name: String): Boolean = name.isNotEmpty()
    private fun isValidEmail(email: String): Boolean =
        Patterns.EMAIL_ADDRESS.matcher(email).matches()

    fun onSignUpChanged(name: String, email: String, password: String) {
        _name.value = name
        _email.value = email
        _password.value = password
        _signUpEnable.value = isValidName(name) && isValidEmail(email) && isValidPassword(password)
        if (_signUpEnable.value == true) {
            emailFound(email)
        }
    }

    fun onSignUpSelected(navController: NavController) {
        val name = name.value.toString()
        val email = email.value.toString()
        val password = password.value.toString()

        if (liveEmailFound.value == false) {
            purgeLogs()
            addNewUser(name, email, password)
            addUSerAuth(email, password)
            navController.popBackStack()
            navController.navigate(AppScreens.LoginScreen.route)
        } else {
            _isError.value = true
        }
    }

    fun onBackSelected(navController: NavController) {
        navController.popBackStack()
        navController.navigate(AppScreens.LoginScreen.route)
        purgeLogs()
    }

    private fun purgeLogs() {
        _email.value = ""
        _name.value = ""
        _password.value = ""
        _signUpEnable.value = false
    }

    private fun emailFound(email: String) {
        ioScope.launch {
            val data = userRepository.getUser(email)
            data.get().addOnSuccessListener { user ->
                if (user.exists()) {
                    liveEmailFound.postValue(true)
                } else
                    liveEmailFound.postValue(false)
            }
        }
    }

    fun tokenDevice() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            tokenDevice.postValue(task.result)
        }
    }

    private fun addNewUser(name: String, email: String, password: String) {
        val user = UserEntity(name, email, password, tokenDevice.value!!)
        userRepository.newUser(user)
    }

    private fun addUSerAuth(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                _userRegistered.value = true
            } else {
                _userNotRegistered.value = true
            }
        }
    }


}