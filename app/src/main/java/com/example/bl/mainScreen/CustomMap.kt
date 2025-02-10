package com.example.bl.mainScreen

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.bl.R
import com.example.bl.dataPlace.PlaceDBEntity
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun CustomMap() {
    val context = LocalContext.current
    val mapProperties = remember { mutableStateOf(MapProperties()) }
    val mapUiSettings = remember { MapUiSettings() }
    val db = Firebase.firestore
    val listPlace = remember { mutableStateOf(emptyList<PlaceDBEntity>()) }
   // val places = remember { mutableStateOf(emptyList<PlaceDBEntity>()) }


    // Загружаем стиль из файла
    LaunchedEffect(Unit) {
        val styleJson = try {
            context.resources.openRawResource(R.raw.style).bufferedReader().use { it.readText() }
        } catch (e: Exception) {
            Log.e("MapStyle", "Не удалось загрузить файл стиля", e)
            null
        }

        styleJson?.let {
            mapProperties.value = mapProperties.value.copy(mapStyleOptions = MapStyleOptions(it))
        }
    }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            LatLng(53.9, 28.0), 5.8f  // Центр Беларуси, масштаб 6.0 для отображения всей страны
        )
    }

    db.collection("place")
        .get()
        .addOnCompleteListener { task ->
            if (task.isSuccessful){
                listPlace.value = task.result.toObjects(PlaceDBEntity::class.java)
            }
        }
        .addOnFailureListener { e ->
            Log.w("Firestore", "Ошибка чтения", e)
        }


    // Отображаем карту с маркерами
    GoogleMap(
        modifier = Modifier
            .fillMaxWidth(),
            //.height(480.dp),
//            .fillMaxWidth()
//            .height(480.dp),
        properties = mapProperties.value,
        uiSettings = mapUiSettings,
        cameraPositionState = cameraPositionState
    ) {
        listPlace.value.forEach { place ->  // добавляем .value к places
            Marker(
                state= MarkerState(position = LatLng(place.point.latitude, place.point.longitude)),
                title = place.name,
                snippet = place.description,
                icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)
 //               icon = BitmapDescriptorFactory.fromPath(R.drawable.logo1.toString())
            )

        }
    }
}