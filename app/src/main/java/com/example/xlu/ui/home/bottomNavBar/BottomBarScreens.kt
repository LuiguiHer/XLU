package com.example.xlu.ui.home.bottomNavBar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.xlu.ui.utils.Utils

sealed class BottomBarScreens(
    val route:String,
    val title: String,
    val icon: ImageVector,
){
    object Home: BottomBarScreens(
        Utils.ROUTE_HOME,
        Utils.HOME,
        Icons.Filled.Home
    )
    object Search: BottomBarScreens(
        Utils.ROUTE_SEARCH,
        Utils.SEARCH,
        Icons.Filled.Search
    )
    object Play: BottomBarScreens(
        Utils.ROUTE_PLAY,
        Utils.PLAY,
        Icons.Filled.PlayArrow
    )
    object Account: BottomBarScreens(
        Utils.ROUTE_ACCOUNT,
        Utils.ACCOUNT,
        Icons.Filled.AccountCircle
    )
    object DetailSearch: BottomBarScreens(
        Utils.ROUTE_DETAILS_SEARCH,
        Utils.DETAILS_MOVIE,
        Icons.Filled.Info
    )
    object DetailHome: BottomBarScreens(
        Utils.ROUTE_DETAILS_HOME,
        Utils.DETAILS_MOVIE,
        Icons.Filled.Info
    )
}
