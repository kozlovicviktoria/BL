package com.example.bl.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.bl.favScreen.viewModel.FavRepository
import com.example.bl.favScreen.FavScreen
import com.example.bl.favScreen.viewModel.FavViewModel
import com.example.bl.favScreen.viewModel.FavViewModelFactory
import com.example.bl.logScreen.viewModel.LoginRepository
import com.example.bl.logScreen.LoginScreen
import com.example.bl.logScreen.viewModel.LoginViewModel
import com.example.bl.logScreen.viewModel.LoginViewModelFactory
import com.example.bl.drawer.MainDrawerScaffold
import com.example.bl.mainScreen.viewModel.MainRepository
import com.example.bl.mainScreen.MainScreen
import com.example.bl.mainScreen.viewModel.MainViewModel
import com.example.bl.mainScreen.viewModel.MainViewModelFactory
import com.example.bl.navigation.data.DetailsNavObject
import com.example.bl.navigation.data.FavNavObject
import com.example.bl.navigation.data.LoginScreenObject
import com.example.bl.navigation.data.MainScreenObject
import com.example.bl.navigation.data.VisitedNavObject
import com.example.bl.placeDetailsScreen.PlaceDetailsScreen
import com.example.bl.placeDetailsScreen.viewModel.PlaceDetailsViewModel
import com.example.bl.placeDetailsScreen.viewModel.PlaceDetailsViewModelFactory
import com.example.bl.placeDetailsScreen.viewModel.PlaceRepository
import com.example.bl.visitedScreen.VisitedScreen
import com.example.bl.visitedScreen.viewModel.VisitedRepository
import com.example.bl.visitedScreen.viewModel.VisitedViewModel
import com.example.bl.visitedScreen.viewModel.VisitedViewModelFactory
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
