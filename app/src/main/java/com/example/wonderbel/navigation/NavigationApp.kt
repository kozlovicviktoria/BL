package com.example.wonderbel.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.wonderbel.favScreen.viewModel.FavRepository
import com.example.wonderbel.favScreen.FavScreen
import com.example.wonderbel.favScreen.viewModel.FavViewModel
import com.example.wonderbel.favScreen.viewModel.FavViewModelFactory
import com.example.wonderbel.logScreen.viewModel.LoginRepository
import com.example.wonderbel.logScreen.LoginScreen
import com.example.wonderbel.logScreen.viewModel.LoginViewModel
import com.example.wonderbel.logScreen.viewModel.LoginViewModelFactory
import com.example.wonderbel.drawer.MainDrawerScaffold
import com.example.wonderbel.mainScreen.viewModel.MainRepository
import com.example.wonderbel.mainScreen.MainScreen
import com.example.wonderbel.mainScreen.viewModel.MainViewModel
import com.example.wonderbel.mainScreen.viewModel.MainViewModelFactory
import com.example.wonderbel.navigation.data.DetailsNavObject
import com.example.wonderbel.navigation.data.FavNavObject
import com.example.wonderbel.navigation.data.LoginScreenObject
import com.example.wonderbel.navigation.data.MainScreenObject
import com.example.wonderbel.navigation.data.VisitedNavObject
import com.example.wonderbel.placeDetailsScreen.PlaceDetailsScreen
import com.example.wonderbel.placeDetailsScreen.viewModel.PlaceDetailsViewModel
import com.example.wonderbel.placeDetailsScreen.viewModel.PlaceDetailsViewModelFactory
import com.example.wonderbel.placeDetailsScreen.viewModel.PlaceRepository
import com.example.wonderbel.visitedScreen.VisitedScreen
import com.example.wonderbel.visitedScreen.viewModel.VisitedRepository
import com.example.wonderbel.visitedScreen.viewModel.VisitedViewModel
import com.example.wonderbel.visitedScreen.viewModel.VisitedViewModelFactory
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
            val factory = LoginViewModelFactory(LoginRepository())
            val viewModel: LoginViewModel = viewModel(factory = factory)
            LoginScreen(viewModel) { navData ->
                navController.navigate(navData)
            }
        }

        composable<MainScreenObject> { navEntry ->
            val navData = navEntry.toRoute<MainScreenObject>()
            val factory = MainViewModelFactory(MainRepository())
            val viewModel: MainViewModel = viewModel(factory = factory)
            MainDrawerScaffold(
                userId = navData.userId,
                email = navData.email,
                navController = navController
            ) {
                MainScreen(navController, viewModel)
            }
        }

        composable<DetailsNavObject> { navEntry ->
            val placeId = navEntry.toRoute<DetailsNavObject>()
            val email = currentUser?.email ?: ""
            val factory = PlaceDetailsViewModelFactory(PlaceRepository(), FavRepository(), VisitedRepository())
            val viewModel: PlaceDetailsViewModel = viewModel(factory = factory)
            MainDrawerScaffold(
                userId = currentUser?.uid ?: "",
                email,
                navController = navController
            ) {
                PlaceDetailsScreen(placeId, email, viewModel)
            }
        }

        composable<FavNavObject> { navEntry ->
            val userId = navEntry.toRoute<FavNavObject>()
            val factory = FavViewModelFactory(FavRepository())
            val viewModel: FavViewModel = viewModel(factory = factory)
            MainDrawerScaffold(
                userId = currentUser?.uid ?: "",
                email = currentUser?.email ?: "",
                navController = navController
            ) {
                FavScreen(userId, navController, viewModel)
            }
        }

        composable<VisitedNavObject> {
            val userId = it.toRoute<VisitedNavObject>()
            val factory = VisitedViewModelFactory(VisitedRepository(), MainRepository())
            val viewModel: VisitedViewModel = viewModel(factory = factory)
            MainDrawerScaffold(
                userId = currentUser?.uid ?: "",
                email = currentUser?.email ?: "",
                navController = navController
            ) {
                VisitedScreen(userId, navController, viewModel)
            }
        }
    }
}
