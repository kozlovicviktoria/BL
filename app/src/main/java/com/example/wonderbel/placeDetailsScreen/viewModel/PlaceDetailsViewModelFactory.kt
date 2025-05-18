package com.example.wonderbel.placeDetailsScreen.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.wonderbel.favScreen.viewModel.FavRepository
import com.example.wonderbel.visitedScreen.viewModel.VisitedRepository

class PlaceDetailsViewModelFactory(
    private val placeRepository: PlaceRepository,
    private val favRepository: FavRepository,
    private val visitedRepository: VisitedRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PlaceDetailsViewModel(placeRepository, favRepository, visitedRepository) as T
    }
}
