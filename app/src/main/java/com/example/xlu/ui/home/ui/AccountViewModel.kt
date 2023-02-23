package com.example.xlu.ui.home.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.xlu.ui.sign_up.data.repository.UserRepositoryFirebase
import com.example.xlu.ui.utils.Utils
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel
@Inject constructor(
    private val userRepository: UserRepositoryFirebase
) : ViewModel() {

    private val auth = FirebaseAuth.getInstance()
    private val iScope = CoroutineScope(Dispatchers.IO)

    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String> = _userName


    fun closedAuthUser(){
        auth.signOut()
    }

    fun getUser(email: String) {
        iScope.launch {
            val dataUser = userRepository.getUser(email)
            dataUser.get().addOnSuccessListener { user ->
                if (user.exists()){
                    _userName.postValue(user.getString(Utils.TABLE_LABEL_NAME))
                }
            }
        }
    }
}

