package com.example.bl.placeDetailsScreen

import com.google.firebase.firestore.GeoPoint
import kotlinx.serialization.Serializable
import org.w3c.dom.Comment

@Serializable
data class DetailsNavObject(
    val imageUrl: String = "",
    var name: String = "",
    var description: String = "",
    var area: String = "",
    var comment: String =""
)
