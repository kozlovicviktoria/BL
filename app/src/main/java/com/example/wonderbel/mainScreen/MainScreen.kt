package com.example.wonderbel.mainScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.wonderbel.bottomMenu.BottomMenu
import com.example.wonderbel.mainScreen.viewModel.MainViewModel


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(
    navController: NavController,
    mainViewModel: MainViewModel = viewModel()) {

    mainViewModel.loadAllPlaces()

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = { BottomMenu(
                onCategoryClick = {category->
                    mainViewModel.selectedCategory = category
                    mainViewModel.loadAllPlaces()
                }
            ) },
        ) {
            CustomMap(mainViewModel.allPlaces.value, navController::navigate)
        }
    }


