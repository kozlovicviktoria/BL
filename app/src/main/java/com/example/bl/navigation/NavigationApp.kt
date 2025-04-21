package com.example.bl.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.bl.favScreen.FavScreen
import com.example.bl.logScreen.LoginScreen
import com.example.bl.mainScreen.MainDrawerScaffold
import com.example.bl.mainScreen.MainScreen
import com.example.bl.placeDetailsScreen.PlaceDetailsScreen
import com.example.bl.visitedScreen.VisitedScreen
import com.google.firebase.auth.FirebaseUser

@Composable
fun NavigationApp(currentUser: FirebaseUser?) {
    val navController = rememberNavController()

    val startDestination = if (currentUser != null) {
        MainScreenObject(currentUser.uid, currentUser.email.toString())
    } else {
        LoginScreenObject
    }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable<LoginScreenObject> {
            LoginScreen { navData ->
                navController.navigate(navData)
            }
        }

        composable<MainScreenObject> { navEntry ->
            val navData = navEntry.toRoute<MainScreenObject>()
            MainDrawerScaffold(
                userId = navData.userId,
                email = navData.email,
                navController = navController
            ) {
                MainScreen(navData, navController)
            }
        }

        composable<DetailsNavObject> { navEntry ->
            val placeId = navEntry.toRoute<DetailsNavObject>()
            val email = currentUser?.email ?: ""

            MainDrawerScaffold(
                userId = currentUser?.uid ?: "",
                email,
                navController = navController
            ) {
                PlaceDetailsScreen(placeId, email)
            }
        }


        composable<FavNavObject> { navEntry ->
            val userId = navEntry.toRoute<FavNavObject>()
            MainDrawerScaffold(
                userId = currentUser?.uid ?: "",
                email = currentUser?.email ?: "",
                navController = navController
            ) { //drawerState ->
                FavScreen(userId, navController)
            }
        }

        composable<VisitedNavObject> {
            val userId = it.toRoute<VisitedNavObject>()
            MainDrawerScaffold(
                userId = currentUser?.uid ?: "",
                email = currentUser?.email ?: "",
                navController = navController
            ) {
                VisitedScreen(userId, navController)
            }
        }
    }
}
