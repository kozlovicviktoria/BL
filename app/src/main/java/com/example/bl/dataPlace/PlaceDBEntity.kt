package com.example.bl.dataPlace

import androidx.compose.ui.text.LinkAnnotation
import com.google.firebase.firestore.GeoPoint
import java.net.URL

data class PlaceDBEntity(
     var name: String = "",
     var point: GeoPoint = GeoPoint(0.0, 0.0),
     var description: String = "",
     var monument: Boolean = false,
     var naturalBeaty: Boolean = false,
     var localCulture: Boolean = false,
     var museun: Boolean = false,
     var attraction: Boolean = false,
     var imageURL: String = ""
)
