package com.example.bl.visitedScreen

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.bl.data.Favorites
import com.example.bl.data.PlaceDBEntity
import com.example.bl.data.Visited
import com.example.bl.favScreen.FavPlaceCard
import com.example.bl.favScreen.fullFavPlaces
import com.example.bl.mainScreen.getAllPlace
import com.example.bl.navigation.VisitedNavObject
import com.example.bl.placeDetailsScreen.getAllPlacesWithFavorites
import com.example.bl.placeDetailsScreen.getAllPlacesWithVisited
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.tasks.await

@Composable
fun VisitedScreen(userId: VisitedNavObject, navController: NavController) {

    val totalPlaces = remember { mutableStateOf(0) }
    val visitedCount = remember { mutableStateOf(0) }
    val db = Firebase.firestore
    val visitedPlaces = remember { mutableStateOf(emptyList<PlaceDBEntity>()) }
    val isVisited = remember { mutableStateOf(true) }
    val vis = remember { mutableStateOf(emptyList<Visited>()) }

    LaunchedEffect(Unit) {
        val snapshot = getAllPlace(db, category = "all")
        totalPlaces.value = snapshot.size
    }

    LaunchedEffect(userId) {
        val visited = getAllPlacesWithVisited(db, userId.userId)
        visitedCount.value = visited.size
    }
    LaunchedEffect(userId.userId) {
        if (userId.userId.isNotBlank()) {
            val viss = getAllPlacesWithVisited(db, userId.userId)
            vis.value = viss
            // Новый вызов suspend-функции
            val fullPlaces = fullVisPlaces(db, viss)
            visitedPlaces.value = fullPlaces
        }
    }

    Column {

        VisitedProgressBar(visitedCount.value, totalPlaces.value)

        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(visitedPlaces.value) { place ->
                VisPlaceCard(place, db, isVisited, vis, userId.userId) { detailsNavObject ->
                    navController.navigate(detailsNavObject)

                    Log.d("VisScreen", "Clicked on place with id: ${place}")
                }
            }
        }
    }
}

suspend fun fullVisPlaces(
    db: FirebaseFirestore,
    favs: List<Visited>
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


