package com.example.wonderbel.placeDetailsScreen.viewModel

import android.content.Context
import android.icu.text.SimpleDateFormat
import android.location.Geocoder
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wonderbel.data.FavoriteEntity
import com.example.wonderbel.data.VisitedEntity
import com.example.wonderbel.favScreen.viewModel.FavRepository
import com.example.wonderbel.data.CommentEntity
import com.example.wonderbel.visitedScreen.viewModel.VisitedRepository
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.Locale

class PlaceDetailsViewModel(
    private val placeRepository: PlaceRepository,
    private val favRepository: FavRepository,
    private val visitedRepository: VisitedRepository
) : ViewModel() {
    var comment by mutableStateOf("")
    var commentsList by mutableStateOf(emptyList<CommentEntity>())
    var isFavorite by mutableStateOf(false)
    var db = Firebase.firestore
    var favPlaces by mutableStateOf(emptyList<FavoriteEntity>())
    var isVisited by mutableStateOf(false)
    var visitedPlaces by mutableStateOf(emptyList<VisitedEntity>())
    var rating by mutableStateOf(0)
    val uid = FirebaseAuth.getInstance().currentUser?.uid.orEmpty()

    fun loadInitialData(placeId: String) {
        viewModelScope.launch {
            if (uid.isNotBlank()) {
                val favs = favRepository.getAllPlacesWithFavorites(db, uid)
                favPlaces = favs
                isFavorite = favs.any { it.key == placeId }

                val visited = visitedRepository.getAllPlacesWithVisited(db, uid)
                visitedPlaces = visited
                isVisited = visited.any { it.key == placeId }

                val comm = placeRepository.getAllComment(db, placeId)
                commentsList = comm
            }
        }
    }

    fun formatTimestamp(timestamp: Timestamp?): String {
        if (timestamp == null) return "Неизвестно"

        val date = timestamp.toDate()
        val formatter = SimpleDateFormat("dd MMMM yyyy, HH:mm", Locale("ru"))
        return formatter.format(date)
    }

    fun toggleFavorite(placeId: String) {
        viewModelScope.launch {
            val updatedFavorites = favRepository.onFavoriteClick(
                db,
                isFavorite,
                uid,
                FavoriteEntity(placeId)
            )
            favPlaces = updatedFavorites
            isFavorite = updatedFavorites.any { it.key == placeId }
        }
    }

    fun toggleVisited(placeId: String) {
        viewModelScope.launch {
            val updatedVisited = visitedRepository.onVisitedClick(
                db,
                isVisited,
                uid,
                VisitedEntity(placeId)
            )
            visitedPlaces = updatedVisited
            isVisited = updatedVisited.any { it.key == placeId }
        }
    }

    fun submitComment(placeId: String, userEmail: String) {
        viewModelScope.launch {
            val newKey = db.collection("places")
                .document(placeId)
                .collection("comments")
                .document()
                .id
            val newComment = CommentEntity(
                key = newKey,
                placeId = placeId,
                userId = uid,
                userEmail = userEmail,
                text = comment,
                time = Timestamp.now(),
                rating = rating
            )
            val updatedComments = placeRepository.onAddComment(db, placeId, newComment)
            commentsList = updatedComments
            comment = "" // очистка поля
            rating = 0
        }
    }

    fun deleteComment(placeId: String, comment: CommentEntity) {
        viewModelScope.launch {
            val updatedComments =
                placeRepository.onDeleteComment(db, placeId, comment)
            commentsList = updatedComments
        }
    }

    fun getAddressFromLatLng(context: Context, lat: Double, lng: Double): String {
        val geocoder = Geocoder(context, Locale("ru"))
        return try {
            val addresses = geocoder.getFromLocation(lat, lng, 1)
            if (!addresses.isNullOrEmpty()) {
                val address = addresses[0]
                val city = address.locality
                val region = address.adminArea
                val fullAddress = address.getAddressLine(0)

                "Город: $city\nОбласть: $region\nАдрес: $fullAddress"
                return fullAddress
            } else {
                "Адрес не найден"
            }
        } catch (e: IOException) {
            e.printStackTrace()
            "Ошибка при получении адреса"
        }
    }
}
