package com.example.xlu.ui.home.bottomNavBar

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.xlu.ui.home.ui.*

@Composable
fun BottomNavGraph(
    navController: NavHostController,
    searchViewModel: SearchViewModel,
    homeViewModel: HomeViewModel,
    accountViewModel: AccountViewModel,
    padding: PaddingValues,
    context: Context
) {
    Column(modifier = Modifier.padding(padding))
    {
        NavHost(navController, startDestination = BottomBarScreens.Home.route){
            composable(route = BottomBarScreens.Home.route){
                HomeScreen(homeViewModel,navController)
            }
            composable(route = BottomBarScreens.Search.route){
                SearchScreen(searchViewModel)
            }
            composable(route = BottomBarScreens.Play.route){
                PlayScreen()
            }
            composable(route = BottomBarScreens.Account.route){
                AccountScreen(accountViewModel,context)
            }

        }
    }

}