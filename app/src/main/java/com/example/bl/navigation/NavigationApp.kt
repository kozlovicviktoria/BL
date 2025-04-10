package com.example.bl.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.bl.logScreen.LoginScreen
import com.example.bl.mainScreen.MainScreen
import com.example.bl.placeDetailsScreen.PlaceDetailsScreen
import com.google.firebase.auth.FirebaseUser

@Composable
fun NavigationApp (currentUser: FirebaseUser?){
    val navController = rememberNavController()

    val startDestination = if (currentUser != null) MainScreenObject(
        currentUser.uid, currentUser.email.toString()
    ) else LoginScreenObject

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable<LoginScreenObject> {
            LoginScreen{ navData ->
                navController.navigate(navData) {

                }
            }
        }
        composable<MainScreenObject> { navEntry ->
            val navData = navEntry.toRoute<MainScreenObject>()
            MainScreen(navData, navController)
        }
        composable<DetailsNavObject> { backStackEntry ->
            val placeId = backStackEntry.toRoute<DetailsNavObject>()
            PlaceDetailsScreen(placeId, navController)
        }
    }
}
