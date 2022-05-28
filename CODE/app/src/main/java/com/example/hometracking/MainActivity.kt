package com.example.hometracking

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.hometracking.ui.theme.HomeTrackingTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            HomeTrackingTheme() {
                // A surface container using the 'background' color from the theme
                val navController = rememberNavController()
                Scaffold(
                    topBar = { TopBar()},
                    bottomBar = { BottomNavigation(navController = navController)}
                ) {
                    Navigation(navController = navController)
                }
            }
        }
    }
}
@Composable
fun Navigation(navController: NavHostController){
    NavHost(
        navController = navController,
        startDestination = NavigationItem.Home.route) {
        composable(NavigationItem.Home.route) {
            HomeScreen()}
        composable(NavigationItem.History.route)
        {
            HistoryScreen(data = listOf("Nguyễn Thuỳ Linh" , "Nguyễn Thuỳ Linh"))
        }
        composable(NavigationItem.Notification.route)
        {
            NotificationScreen()
        }
        composable(NavigationItem.Account.route)
        {
            AccountScreen()
        }

    }

}

@Composable
fun TopBar(){
    TopAppBar(
        contentColor = Color.White,
//        backgroundColor = colorResource(id = androidx.compose.material.R.color.colorPrimary),



    ) {

    }
}

@Composable
fun BottomNavigation(navController: NavHostController){
    var items = listOf(
        NavigationItem.Home,
        NavigationItem.History,
        NavigationItem.Notification,
        NavigationItem.Account,
    )
    androidx.compose.material.BottomNavigation(
//        backgroundColor= colorResource(id = R.color.colorPrimary),
        contentColor = Color.White
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach{
                item ->
            BottomNavigationItem(
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route){
                        navController.graph.startDestinationRoute?.let{
                                route -> popUpTo(route){
                            saveState = true
                        }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = { Icon(painter = painterResource(id = item.icon),
                    contentDescription = item.title)
                },
                label = {Text(text = item.title)},
                selectedContentColor = Color.White,
                unselectedContentColor = Color.White.copy(0.4f),
                alwaysShowLabel = true
            )
        }


    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    HomeTrackingTheme() {
    }
}