package com.example.bl.visitedScreen.viewModel

import android.util.Log
import com.example.bl.data.PlaceDBEntity
import com.example.bl.data.VisitedEntity
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.tasks.await

class VisitedRepository {

    suspend fun fullVisPlaces(
        db: FirebaseFirestore,
        favs: List<VisitedEntity>
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

    suspend fun onVisitedClick(
        db: FirebaseFirestore,
        isVisited: Boolean,
        uid: String,
        visited: VisitedEntity
    ): List<VisitedEntity> {
        val docRef = db.collection("users")
            .document(uid)
            .collection("visited")
            .document(visited.key)

        return try {
            if (!isVisited) {
                docRef.set(visited).await()
                Log.d("FAVORITE", "Добавлено в избранное")
            } else {
                docRef.delete().await()
                Log.d("FAVORITE", "Удалено из избранного")
            }

            getAllPlacesWithVisited(db, uid) // ← вернём обновлённый список
        } catch (e: Exception) {
            Log.e("FAVORITE", "Ошибка при обновлении избранного", e)
            emptyList()
        }
    }

    suspend fun getAllPlacesWithVisited(
        db: FirebaseFirestore,
        userId: String
    ): List<VisitedEntity> {
        return try {
            val snapshot = db.collection("users")
                .document(userId)
                .collection("visited")
                .get()
                .await()

            snapshot.documents.mapNotNull { it.toObject(VisitedEntity::class.java) }
        } catch (e: Exception) {
            emptyList()
        }
    }

}