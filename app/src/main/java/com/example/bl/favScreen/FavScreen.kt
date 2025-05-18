package com.example.bl.favScreen

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.bl.favScreen.viewModel.FavViewModel
import com.example.bl.navigation.data.FavNavObject

@Composable
fun FavScreen(userId: FavNavObject,
              navController: NavController,
              favViewModel: FavViewModel = viewModel()) {

    favViewModel.FavList(userId.userId)

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
                items(favViewModel.favPlaces) { place ->
            FavPlaceCard(place, userId.userId, favViewModel) {
                    detailsNavObject ->
                navController.navigate(detailsNavObject)

                Log.d("FavScreen", "Clicked on place with id: ${place}")
            }
        }
    }
}
