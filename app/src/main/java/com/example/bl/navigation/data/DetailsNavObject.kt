package com.example.bl.navigation.data

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

