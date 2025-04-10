package com.example.bl.dataPlace

import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.GeoPoint

data class PlaceDBEntity(
     var name: String = "",
     var point: GeoPoint = GeoPoint(0.0, 0.0),
     var description: String = "",
     var monument: Boolean = false,
     var naturalBeaty: Boolean = false,
     var localCulture: Boolean = false,
     var museum: Boolean = false,
     var attraction: Boolean = false,
     var imageUrl: String = ""
)
