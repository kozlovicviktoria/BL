package com.example.wonderbel.mainScreen.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wonderbel.data.PlaceDBEntity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: MainRepository
) : ViewModel() {
    val allPlaces = mutableStateOf(emptyList<PlaceDBEntity>())
    val db = Firebase.firestore
    var selectedCategory by mutableStateOf("all") // Для хранения выбранной категории

    fun loadAllPlaces() {
        viewModelScope.launch {
            repository.loadPlaces(selectedCategory, db, allPlaces)
        }
    }
}