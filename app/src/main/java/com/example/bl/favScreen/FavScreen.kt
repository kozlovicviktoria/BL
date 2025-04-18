package com.example.bl.favScreen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.bl.data.Favorites
import com.example.bl.data.PlaceDBEntity
import com.example.bl.navigation.DetailsNavObject
import com.example.bl.navigation.FavNavObject
import com.example.bl.navigation.MainScreenObject
import com.example.bl.navigation.NavigationApp
import com.example.bl.navigation.VisitedNavObject
import com.example.bl.placeDetailsScreen.getAllPlacesWithFavorites
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@Composable
fun FavScreen(userId: FavNavObject,
              navController: NavController) {

    val db = Firebase.firestore
    val favPlaces = remember { mutableStateOf(emptyList<PlaceDBEntity>()) }

    LaunchedEffect(Unit) {
        getAllPlacesWithFavorites(db, userId.userId) { favs ->
            fullFavPlaces(db, favs) { fullPlaces ->
                favPlaces.value = fullPlaces
            }
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(favPlaces.value) { place ->
            FavPlaceCard(place = place) {

                    detailsNavObject ->
                // передача объекта в SavedStateHandle
                navController.currentBackStackEntry?.savedStateHandle?.set(
                    key = "place",
                    value = detailsNavObject
                )
                navController.navigate("PlaceDetailsScreen")
                Log.d("FavScreen", "Clicked on place with id: ${place}")

            }
        }
    }
}


fun fullFavPlaces(
    db: FirebaseFirestore,
    favs: List<Favorites>,
    onComplete: (List<PlaceDBEntity>) -> Unit
) {
    val favIds = favs.map { it.key }
    val places = mutableListOf<PlaceDBEntity>()

    val tasks = favIds.map { id ->
        db.collection("places").document(id).get()
    }

    Tasks.whenAllSuccess<DocumentSnapshot>(tasks).addOnSuccessListener { documents ->
        documents.forEach { doc ->
            doc.toObject(PlaceDBEntity::class.java)?.apply { id = doc.id }?.let {
                places.add(it)
            }
        }
        onComplete(places)
    }.addOnFailureListener { e ->
        Log.w("Firestore", "Ошибка загрузки избранных мест", e)
        onComplete(emptyList())
    }
}

