package com.example.bl.favScreen.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class FavViewModelFactory(
    private val favRepository: FavRepository
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return FavViewModel(favRepository) as T
        }
    }
