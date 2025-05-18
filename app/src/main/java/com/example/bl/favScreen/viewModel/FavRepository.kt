package com.example.bl.favScreen.viewModel

import android.util.Log
import com.example.bl.data.FavoriteEntity
import com.example.bl.data.PlaceDBEntity
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.tasks.await

class FavRepository() {

    suspend fun fullFavPlaces(
        db: FirebaseFirestore,
        favs: List<FavoriteEntity>
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

    suspend fun getAllPlacesWithFavorites(
        db: FirebaseFirestore,
        userId: String
    ): List<FavoriteEntity> {
        return try {
            val snapshot = db.collection("users")
                .document(userId)
                .collection("favorites")
                .get()
                .await()

            snapshot.documents.mapNotNull { it.toObject(FavoriteEntity::class.java) }
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun onFavoriteClick(
        db: FirebaseFirestore,
        isFavorite: Boolean,
        uid: String,
        favorite: FavoriteEntity
    ): List<FavoriteEntity> {

        val docRef = db.collection("users")
            .document(uid)
            .collection("favorites")
            .document(favorite.key)

        return try {
            if (!isFavorite) {
                docRef.set(favorite).await()
                Log.d("FAVORITE", "Добавлено в избранное")
            } else {
                docRef.delete().await()
                Log.d("FAVORITE", "Удалено из избранного")
            }

            getAllPlacesWithFavorites(db, uid)
        } catch (e: Exception) {
            Log.e("FAVORITE", "Ошибка при обновлении избранного", e)
            emptyList()
        }
    }

}