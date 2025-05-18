package com.example.wonderbel.visitedScreen

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.wonderbel.navigation.data.VisitedNavObject
import com.example.wonderbel.visitedScreen.ui.VisitedProgressBar
import com.example.wonderbel.visitedScreen.viewModel.VisitedViewModel

@Composable
fun VisitedScreen(userId: VisitedNavObject,
                  navController: NavController,
                  visitedViewModel: VisitedViewModel = viewModel()
) {

    visitedViewModel.getCountPlaces()
    visitedViewModel.getCountVisitedPlaces(userId.userId)
    visitedViewModel.getAllVisitedPlaces(userId.userId)

    Column {
        VisitedProgressBar(visitedViewModel.visitedCount, visitedViewModel.totalPlaces)

        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(visitedViewModel.visitedPlaces) { place ->
                VisPlaceCard(place, userId.userId, visitedViewModel) { detailsNavObject ->
                    navController.navigate(detailsNavObject)

                    Log.d("VisScreen", "Clicked on place with id: ${place}")
                }
            }
        }
    }
}




