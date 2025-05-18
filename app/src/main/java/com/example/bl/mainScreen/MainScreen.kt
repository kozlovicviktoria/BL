package com.example.bl.mainScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.bl.bottomMenu.BottomMenu
import com.example.bl.mainScreen.viewModel.MainViewModel
import com.example.bl.navigation.data.MainScreenObject


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


