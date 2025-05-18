package com.example.bl.visitedScreen.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bl.mainScreen.viewModel.MainRepository

class VisitedViewModelFactory (
    private val visitedRepository: VisitedRepository,
    private val mainRepository: MainRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return VisitedViewModel(visitedRepository, mainRepository) as T
    }
}