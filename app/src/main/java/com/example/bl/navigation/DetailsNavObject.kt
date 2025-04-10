package com.example.bl.navigation

import com.google.firebase.firestore.GeoPoint
import kotlinx.serialization.Serializable

@Serializable
data class DetailsNavObject(
    var name: String = "",
    var description: String = "",
    val imageUrl: String = "",
    var lat: String = "",
    var lng: String = ""
)
