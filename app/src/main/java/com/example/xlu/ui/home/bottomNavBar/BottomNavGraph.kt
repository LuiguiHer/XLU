package com.example.xlu.ui.home.bottomNavBar

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.xlu.roomDB.model.BestMovies
import com.example.xlu.ui.home.model.Movies
import com.example.xlu.ui.home.ui.*
import com.example.xlu.ui.home.ui.details.DetailHomeScreen
import com.example.xlu.ui.home.ui.details.DetailHomeViewModel
import com.example.xlu.ui.home.ui.details.DetailSearchScreen
import com.example.xlu.ui.home.ui.details.DetailSearchViewModel

@Composable
fun BottomNavGraph(
    navController: NavHostController,
    searchViewModel: SearchViewModel,
    movieBySearch: Movies,
    movieByHome: BestMovies,
    detailSearchViewModel: DetailSearchViewModel,
    detailHomeViewModel: DetailHomeViewModel,
    homeViewModel: HomeViewModel,
    accountViewModel: AccountViewModel,
    padding: PaddingValues
) {
    Column(modifier = Modifier.padding(padding))
    {
        NavHost(navController, startDestination = BottomBarScreens.Home.route){
            composable(route = BottomBarScreens.Home.route){
                HomeScreen(homeViewModel,navController)
            }
            composable(route = BottomBarScreens.Search.route){
                SearchScreen(searchViewModel,navController)
            }
            composable(route = BottomBarScreens.Play.route){
                PlayScreen()
            }
            composable(route = BottomBarScreens.Account.route){
                AccountScreen(accountViewModel)
            }
            composable(route = BottomBarScreens.DetailSearch.route){
                DetailSearchScreen(movieBySearch,navController,detailSearchViewModel)
            }
            composable(route = BottomBarScreens.DetailHome.route){
                DetailHomeScreen(movieByHome,navController,detailHomeViewModel)
            }

        }
    }

}