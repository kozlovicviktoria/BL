package com.example.bl.navigation

import android.os.Parcelable
import com.example.bl.data.PlaceDBEntity
import com.google.firebase.firestore.GeoPoint
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
data class DetailsNavObject(
    var id: String = "",
    var name: String = "",
    var description: String = "",
    val imageUrl: String = "",
    var lat: String = "",
    var lng: String = "",
    var isFavorite: Boolean = false
)

//@Parcelize
//data class DetailsNavObject(
//    val id: String,
//    val name: String,
//    val description: String,
//    val imageUrl: String,
//    val lat: String,
//    val lng: String,
//    val isFavorite: Boolean
//) : Parcelable
