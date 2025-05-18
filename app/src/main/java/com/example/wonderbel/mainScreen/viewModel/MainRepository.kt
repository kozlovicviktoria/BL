package com.example.wonderbel.mainScreen.viewModel

import androidx.compose.runtime.MutableState
import com.example.wonderbel.data.PlaceDBEntity
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class MainRepository {

    suspend fun loadPlaces(category: String, db: FirebaseFirestore, allPlaces: MutableState<List<PlaceDBEntity>>) {
        val place = getAllPlace(db, category)
        allPlaces.value = place
    }

    suspend fun getAllPlace(
        db: FirebaseFirestore,
        category: String
    ): List<PlaceDBEntity> {
        return try {
            val query = if (category == "all") {
                db.collection("places")
            } else {
                db.collection("places").whereEqualTo(category, true)
            }
            val snapshot = query
                .get()
                .await()
            snapshot.documents.mapNotNull { doc ->
                val place = doc.toObject(PlaceDBEntity::class.java)
                place?.apply { id = doc.id }
            }
        } catch (e: Exception){
            emptyList()
        }
    }


}