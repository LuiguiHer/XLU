package com.example.xlu.ui.sign_up.data.repository

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.xlu.ui.home.model.MovieSelected
import com.example.xlu.ui.sign_up.model.UserEntity
import com.google.firebase.auth.FirebaseAuth
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
    companion object{
        const val TABLE_NAME_USERS= "Users"
        const val COLLECTION_FAVORITE_MOVIE= "FavoriteMovies"
    }
    private val db =FirebaseFirestore.getInstance()


    fun newUser(user: UserEntity){
        try {
            users.document(user.email).set(user)

        } catch (e: Exception){
            e.printStackTrace()
        }
    }

    fun getUser(email: String): DocumentReference = db.collection(TABLE_NAME_USERS).document(email)

    fun getFavoriteMovie(user: String,movie: MovieSelected) =
        db.collection(TABLE_NAME_USERS).document(user).collection(COLLECTION_FAVORITE_MOVIE)
            .document(movie.id_Movie.toString())

    fun addToMyFavoriteMovie(user: String,movie: MovieSelected) =
        db.collection(TABLE_NAME_USERS).document(user).collection(COLLECTION_FAVORITE_MOVIE)
            .document(movie.id_Movie.toString())
            .set(movie)

    fun deleteMyFavoriteMovie(user: String,movie: MovieSelected) =
        db.collection(TABLE_NAME_USERS).document(user).collection(COLLECTION_FAVORITE_MOVIE)
            .document(movie.id_Movie.toString())
            .delete()

    fun getListFavoriteMovies(user: String) =
        db.collection(TABLE_NAME_USERS).document(user).collection(COLLECTION_FAVORITE_MOVIE)
}