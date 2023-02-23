package com.example.xlu.ui.sign_up.data.repository

import com.example.xlu.ui.sign_up.model.UserEntity
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryFirebase
@Inject constructor(
    private val users: CollectionReference
){
    private val db =FirebaseFirestore.getInstance()


    fun newUser(user: UserEntity){
        try {
            users.document(user.email).set(user)

        } catch (e: Exception){
            e.printStackTrace()
        }
    }

    fun getUser(email: String): DocumentReference = db.collection("Users").document(email)

}