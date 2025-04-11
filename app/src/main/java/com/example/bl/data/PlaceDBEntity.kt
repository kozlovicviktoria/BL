package com.example.bl.data

import com.google.firebase.firestore.GeoPoint

data class PlaceDBEntity(
     var id: String = "",
     var name: String = "",
     var point: GeoPoint = GeoPoint(0.0, 0.0),
     var description: String = "",
     var monument: Boolean = false,
     var naturalBeaty: Boolean = false,
     var localCulture: Boolean = false,
     var museum: Boolean = false,
     var attraction: Boolean = false,
     var imageUrl: String = "",
     var isFavorite: Boolean = false
)
