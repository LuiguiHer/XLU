package com.example.xlu.ui.sign_up.model

data class UserEntity (
    var name: String,
    val email: String,
    val password: String,
    val tokenDevice: String
    )