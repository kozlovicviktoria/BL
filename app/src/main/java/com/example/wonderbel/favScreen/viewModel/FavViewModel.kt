package com.example.wonderbel.favScreen.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wonderbel.data.FavoriteEntity
import com.example.wonderbel.data.PlaceDBEntity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class FavViewModel(
    private val repository: FavRepository
): ViewModel() {
    val db = Firebase.firestore
    var favPlaces by mutableStateOf(emptyList<PlaceDBEntity>())
    var isFavorite by mutableStateOf(true)
    var favs by mutableStateOf(emptyList<FavoriteEntity>())

    fun FavList(userId: String) {
        viewModelScope.launch {
            if (userId.isNotBlank()) {
                val favors = repository.getAllPlacesWithFavorites(db, userId)
                favs = favors
                // Новый вызов suspend-функции
                val fullPlaces = repository.fullFavPlaces(db, favors)
                favPlaces = fullPlaces
            }
        }
    }

        fun FavUpdate(userId: String, placeId: String) {
            viewModelScope.launch {
                val updatedFavs =
                    repository.onFavoriteClick(db, isFavorite, userId, FavoriteEntity(placeId))
                favs = updatedFavs
                isFavorite = updatedFavs.any { it.key == placeId }
            }
        }

    }
