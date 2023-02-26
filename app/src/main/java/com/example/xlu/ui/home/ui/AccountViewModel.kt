package com.example.xlu.ui.home.ui

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.xlu.ui.home.model.ftp.FTPClientWrapper
import com.example.xlu.ui.sign_up.data.repository.UserRepositoryFirebase
import com.example.xlu.ui.sign_up.model.UserEntity
import com.example.xlu.ui.utils.Utils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel
@Inject constructor(
    private val userRepository: UserRepositoryFirebase
) : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val ftp = FTPClientWrapper()
    private val auth = FirebaseAuth.getInstance()
    private val iScope = CoroutineScope(Dispatchers.IO)
    private val docRef = db.collection("Users")



    private val _remoteUser = MutableLiveData<UserEntity>()
    val remoteUser: LiveData<UserEntity> = _remoteUser


    fun upLoadImageProfile(context: Context,uri:Uri?){
        val token  = remoteUser.value!!.tokenUser
        val fileName = remoteUser.value!!.email
        val codeUpdate = remoteUser.value!!.codeUpdate + 1
        val url = Utils.URL_IMAGE_PROFILE
        viewModelScope.launch(Dispatchers.IO){
            ftp.connect()
            if (uri != null){
                val sendImage = ftp.uploadFile(uri,fileName,token,context,codeUpdate.toString())
                val urlUpdate = "$url/$token/$fileName$codeUpdate.jpg"
                if (sendImage){
                    updateUrlCodeUpdate(fileName,codeUpdate,urlUpdate)
                }
                ftp.disconnect()
            }
        }
    }

    private fun updateUrlCodeUpdate(email: String,codeUpdate: Int, newUrl: String){
        iScope.launch {
            val data = userRepository.getUser(email)
            data.get().addOnSuccessListener { user ->
                if (user.exists()) {
                    docRef.document(email).update(Utils.TABLE_LABEL_CODE_UPDATE, codeUpdate)
                    docRef.document(email).update(Utils.TABLE_LABEL_URL_PROFILE, newUrl)
                    getUser(email)
                }
            }
        }
    }

    fun closedAuthUser(){
        auth.signOut()
    }

    fun getUser(email: String) {
        val remoteUser = UserEntity()
        iScope.launch {
            val dataUser = userRepository.getUser(email)
            dataUser.get().addOnSuccessListener { user ->
                if (user.exists()){
                    remoteUser.name = user.getString(Utils.TABLE_LABEL_NAME)!!
                    remoteUser.email = user.getString(Utils.TABLE_LABEL_EMAIL)!!
                    remoteUser.password = user.getString(Utils.TABLE_LABEL_PASSWORD)!!
                    remoteUser.codeUpdate = user.getLong(Utils.TABLE_LABEL_CODE_UPDATE)!!.toInt()
                    remoteUser.urlProfile = user.getString(Utils.TABLE_LABEL_URL_PROFILE)!!
                    remoteUser.tokenDevice = user.getString(Utils.TABLE_LABEL_TOKEN_DEVICE)!!
                    remoteUser.tokenUser = user.getString(Utils.TABLE_LABEL_TOKEN_USER)!!
                    _remoteUser.postValue(remoteUser)
                }
            }
        }
    }
}

