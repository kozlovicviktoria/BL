package com.example.bl.dataPlace

import com.google.firebase.firestore.GeoPoint

data class PlaceDBEntity(
     var name: String = "",
     var point: GeoPoint = GeoPoint(0.0, 0.0),
     var description: String = "",
     var area: String = "",
     var monument: Boolean = false,
     var naturalBeaty: Boolean = false,
     var localCulture: Boolean = false
)
