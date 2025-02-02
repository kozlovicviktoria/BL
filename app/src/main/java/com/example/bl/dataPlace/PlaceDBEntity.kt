package com.example.bl.dataPlace

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.GeoPoint
import com.google.protobuf.Internal.DoubleList

//@Entity(tableName = "places")
data class PlaceDBEntity(
     var name: String = "",
     var point: GeoPoint = GeoPoint(0.0, 0.0),
     var description: String = "",
     var area: String = "",
     var monument: Boolean = false,
     var naturalBeaty: Boolean = false,
     var localCulture: Boolean = false
)
