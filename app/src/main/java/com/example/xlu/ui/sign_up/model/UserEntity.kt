package com.example.xlu.ui.sign_up.model

data class UserEntity (
    var name: String = "",
    var email: String = "",
    var password: String = "",
    var tokenDevice: String = "",
    var codeUpdate: Int = 0,
    var urlProfile: String = "",
    var tokenUser: String = ""
    )