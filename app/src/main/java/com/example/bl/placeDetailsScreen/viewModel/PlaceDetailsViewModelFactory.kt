package com.example.bl.placeDetailsScreen.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bl.favScreen.viewModel.FavRepository
import com.example.bl.visitedScreen.viewModel.VisitedRepository
import com.example.bl.visitedScreen.viewModel.VisitedViewModel

class PlaceDetailsViewModelFactory(
    private val placeRepository: PlaceRepository,
    private val favRepository: FavRepository,
    private val visitedRepository: VisitedRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PlaceDetailsViewModel(placeRepository, favRepository, visitedRepository) as T
    }
}
