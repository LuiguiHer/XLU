package com.example.xlu.ui.home.ui.details

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.xlu.ui.home.model.RetrofitConfig

class DetailSearchViewModel: ViewModel() {
    val urlImage:String = RetrofitConfig.URL_IMAGE

    fun onBackSelected(navController: NavController) {
        navController.popBackStack()
    }
}