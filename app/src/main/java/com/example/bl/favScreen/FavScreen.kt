package com.example.bl.favScreen

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.bl.data.Favorites
import com.example.bl.data.PlaceDBEntity
import com.example.bl.navigation.FavNavObject
import com.example.bl.placeDetailsScreen.getAllPlacesWithFavorites
import com.example.bl.placeDetailsScreen.getAllPlacesWithVisited
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.tasks.await

@Composable
fun FavScreen(userId: FavNavObject,
              navController: NavController) {

    val db = Firebase.firestore
    val favPlaces = remember { mutableStateOf(emptyList<PlaceDBEntity>()) }
    val isFavorite = remember { mutableStateOf(true) }
    val favs = remember { mutableStateOf(emptyList<Favorites>()) }

    LaunchedEffect(userId.userId) {
        if (userId.userId.isNotBlank()) {
            val favors = getAllPlacesWithFavorites(db, userId.userId)
                favs.value = favors
                // Новый вызов suspend-функции
                val fullPlaces = fullFavPlaces(db, favors)
                favPlaces.value = fullPlaces
            }
        }


//    LaunchedEffect(Unit) {
//        getAllPlacesWithFavorites(db, userId.userId) { favors ->
//            favs.value = favors
//            fullFavPlaces(db, favors) { fullPlaces ->
//                favPlaces.value = fullPlaces
//            }
//        }
//    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(favPlaces.value) { place ->
            FavPlaceCard(place, db, isFavorite, favs, userId.userId) {
                    detailsNavObject ->
                navController.navigate(detailsNavObject)

                Log.d("FavScreen", "Clicked on place with id: ${place}")
            }
        }
    }
}
suspend fun fullFavPlaces(
    db: FirebaseFirestore,
    favs: List<Favorites>
): List<PlaceDBEntity> = coroutineScope {
    val favIds = favs.map { it.key }
    val tasks = favIds.map { id ->
        async {
            try {
                val doc = db.collection("places").document(id).get().await()
                doc.toObject(PlaceDBEntity::class.java)?.apply { this.id = doc.id }
            } catch (e: Exception) {
                Log.w("Firestore", "Ошибка загрузки места: $id", e)
                null
            }
        }
    }
    tasks.awaitAll().filterNotNull()
}


//fun fullFavPlaces(
//    db: FirebaseFirestore,
//    favs: List<Favorites>,
//    onComplete: (List<PlaceDBEntity>) -> Unit
//) {
//    val favIds = favs.map { it.key }
//    val places = mutableListOf<PlaceDBEntity>()
//
//    val tasks = favIds.map { id ->
//        db.collection("places").document(id).get()
//    }
//
//    Tasks.whenAllSuccess<DocumentSnapshot>(tasks).addOnSuccessListener { documents ->
//        documents.forEach { doc ->
//            doc.toObject(PlaceDBEntity::class.java)?.apply { id = doc.id }?.let {
//                places.add(it)
//            }
//        }
//        onComplete(places)
//    }.addOnFailureListener { e ->
//        Log.w("Firestore", "Ошибка загрузки избранных мест", e)
//        onComplete(emptyList())
//    }
//}

