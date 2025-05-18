package com.example.wonderbel.mainScreen

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.wonderbel.R
import com.example.wonderbel.bottomMenu.BottomMenuItem
import com.example.wonderbel.data.PlaceDBEntity
import com.example.wonderbel.navigation.data.DetailsNavObject
import com.example.wonderbel.ui.theme.BottomButtonTrueColor
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun CustomMap(allPlaces: List<PlaceDBEntity>,
              onNavigationDetailsScreen: (DetailsNavObject) -> Unit) {

    val context = LocalContext.current
    val mapProperties = remember { mutableStateOf(MapProperties()) }
    val mapUiSettings = remember {
        MapUiSettings(
            zoomControlsEnabled = false,
            mapToolbarEnabled = false,
            myLocationButtonEnabled = false
        )
    }
    val selectedMarker = remember { mutableStateOf<PlaceDBEntity?>(null) }

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
            LatLng(53.9, 28.0), 5.8f
        )
    }

  //   Отображаем карту с маркерами
    GoogleMap(
        modifier = Modifier
            .fillMaxSize(),
        properties = mapProperties.value,
        uiSettings = mapUiSettings,
        cameraPositionState = cameraPositionState
    ) {
         allPlaces.forEach { place ->
            Marker(
                state = MarkerState(position = LatLng(place.point.latitude, place.point.longitude)),
                title = place.name,
                snippet = place.description,
                icon = bitmapDescriptorFromVector(context, iconPlace(place)),
                onClick = {
                    if (selectedMarker.value?.name == place.name) {
                        onNavigationDetailsScreen(
                            DetailsNavObject(
                            place.id,
                            place.name,
                            place.description,
                            place.imageUrl,
                            place.point.latitude.toString(),
                            place.point.longitude.toString(),
                            place.isFavorite
                        )
                        )
                        // Повторный клик — переходим на экран деталей
                    } else {
                        // Первый клик — просто выделяем маркер
                        selectedMarker.value = place
                    }
                    false // возвращаем false, чтобы инфо-окно все равно показалось
                }
            )
        }

    }

        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Bottom
        ) {
            SmallFloatingActionButton(
                onClick = {
                    cameraPositionState.move(CameraUpdateFactory.zoomIn())
                },
                containerColor = BottomButtonTrueColor,
                modifier = Modifier
                    .padding(bottom = 10.dp, end = 10.dp)
            ) {
                Icon(painter = painterResource(R.drawable.zoom_in), contentDescription = "zoomIn")
            }
            SmallFloatingActionButton(
                onClick = {
                    cameraPositionState.move(CameraUpdateFactory.zoomOut())
                },
                containerColor = BottomButtonTrueColor,
                modifier = Modifier
                    .padding(bottom = 10.dp, end = 10.dp)
            ) {
                Icon(painter = painterResource(R.drawable.zoom_out), contentDescription = "zoomOut")
            }
            SmallFloatingActionButton(
                onClick = {


                },
                containerColor = BottomButtonTrueColor,
                modifier = Modifier
                    .padding(bottom = 140.dp, end = 10.dp)
            ) {
                Icon(Icons.Default.LocationOn, contentDescription = "zoomOut")
            }
        }


}

fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor {
    val vectorDrawable = ContextCompat.getDrawable(context, vectorResId) ?: return BitmapDescriptorFactory.defaultMarker()
    val size = 70
    val bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
    val canvas = android.graphics.Canvas(bitmap)

    vectorDrawable.setBounds(0, 0, size, size)
    vectorDrawable.draw(canvas)

    return BitmapDescriptorFactory.fromBitmap(bitmap)
}


fun iconPlace(place: PlaceDBEntity): Int {
    var placeBoolean = true
    var root: Int

    when (placeBoolean) {
        place.monument -> {
            root = BottomMenuItem.Monument.icon
        }

        place.attraction -> {
            root = BottomMenuItem.Attraction.icon
        }

        place.localCulture -> {
            root = BottomMenuItem.LocalCulture.icon
        }

        place.museum -> {
                root = BottomMenuItem.Museum.icon
            }
        place.naturalBeaty -> {
                root = BottomMenuItem.Nature.icon
            }
        true -> TODO()
        false -> TODO()
    }
    return root
}








