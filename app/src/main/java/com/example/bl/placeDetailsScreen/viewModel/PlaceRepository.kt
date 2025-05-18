package com.example.bl.placeDetailsScreen.viewModel

import android.util.Log
import com.example.bl.data.VisitedEntity
import com.example.bl.data.CommentEntity
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class PlaceRepository() {

    suspend fun getAllComment(
        db: FirebaseFirestore,
        placeId: String
    ): List<CommentEntity> {
        return try {
            val snapshot = db.collection("places")
                .document(placeId)
                .collection("comments")
                .get()
                .await()
            snapshot.documents.mapNotNull { doc ->
                val comm = doc.toObject(CommentEntity::class.java)
                comm?.apply { key = doc.id }
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun onAddComment(
        db: FirebaseFirestore,
        placeId: String,
        comment: CommentEntity
    ): List<CommentEntity> {
        val docRef = db.collection("places")
            .document(placeId)
            .collection("comments")
            .document(comment.key)

        return try {
            docRef.set(comment).await()
            Log.d("FAVORITE", "Комментарий добавлен")
            getAllComment(db, placeId) // ← вернём обновлённый список
        } catch (e: Exception) {
            Log.e("FAVORITE", "Ошибка при добавлении комментария", e)
            emptyList()
        }
    }

    suspend fun onDeleteComment(
        db: FirebaseFirestore,
        placeId: String,
        comment: CommentEntity
    ): List<CommentEntity> {
        val docRef = db.collection("places")
            .document(placeId)
            .collection("comments")
            .document(comment.key)

        return try {
            docRef.delete().await()
            Log.d("FAVORITE", "Комментарий удален")
            getAllComment(db, placeId) // ← вернём обновлённый список
        } catch (e: Exception) {
            Log.e("FAVORITE", "Ошибка при удалении комментария", e)
            emptyList()
        }
    }
}