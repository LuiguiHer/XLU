package com.example.xlu.ui.home.bottomNavBar

import android.content.Context
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.xlu.ui.home.ui.AccountViewModel
import com.example.xlu.ui.home.ui.HomeViewModel
import com.example.xlu.ui.home.ui.SearchViewModel

@Composable
fun BottomNavBar(
    searchViewModel: SearchViewModel,
    homeViewModel: HomeViewModel,
    accountViewModel: AccountViewModel,
    context: Context
){
    val navController = rememberNavController()
    val navigationItems = listOf(
        BottomBarScreens.Home,
        BottomBarScreens.Search,
        BottomBarScreens.Play,
        BottomBarScreens.Account
    )
    Scaffold(
        bottomBar = { BottomNavBarItems(navController = navController, items = navigationItems) }
    ) {padding ->
        BottomNavGraph(
            navController,
            searchViewModel,
            homeViewModel,
            accountViewModel,
            padding,
            context
        )
    }
}

@Composable
fun BottomNavBarItems(
    navController: NavHostController,
    items: List<BottomBarScreens>
){
    val currentRoute = currentRoute(navController = navController)
    BottomNavigation(modifier =Modifier
        .clip(RoundedCornerShape(15.dp, 15.dp, 0.dp, 0.dp)),
        backgroundColor = Color(0xFF161616)
    )
    {
        items.forEach {screen ->
            BottomNavigationItem(
                icon = { Icon(imageVector = screen.icon, contentDescription = screen.title, modifier = Modifier.size(30.dp)) },
                label = { Text(text = "") },
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(screen.route){
                        popUpTo(navController.graph.findStartDestination().id){
                            saveState = true
                        }
                        launchSingleTop = true
                    }
                },
                alwaysShowLabel = false,
                selectedContentColor = Color(0xFFFF460E),
                unselectedContentColor = Color(0xFF2E2E2E)
            )
        }
    }
}

@Composable
fun currentRoute(navController: NavHostController):String?{
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}