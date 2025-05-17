package com.example.bl.placeDetailsScreen

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import java.util.Locale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.bl.R
import com.example.bl.data.Favorites
import com.example.bl.data.Visited
import com.example.bl.logScreen.LoginButton
import com.example.bl.logScreen.RoundCornerTextField
import com.example.bl.navigation.DetailsNavObject
import com.example.bl.ui.theme.DrawerColor
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.io.IOException


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PlaceDetailsScreen(placeId: DetailsNavObject, userEmail: String) {

    val comment = remember { mutableStateOf("") }
    val commentsList = remember { mutableStateOf(emptyList<CommentObject>()) }
    val firestore = remember { Firebase.firestore }
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    val isFavorite = remember { mutableStateOf(false) }
    val db = Firebase.firestore
    val favPlaces = remember { mutableStateOf(emptyList<Favorites>()) }
    val uid = FirebaseAuth.getInstance().currentUser?.uid.orEmpty()
    val isVisited = remember { mutableStateOf(false) }
    val visitedPlaces = remember { mutableStateOf(emptyList<Visited>()) }
    val coroutineScope = rememberCoroutineScope()
    val rating = remember { mutableStateOf(0) }

    LaunchedEffect(uid) {
        if (uid.isNotBlank()) {
            val favs = getAllPlacesWithFavorites(db, uid)
            favPlaces.value = favs
            isFavorite.value = favs.any { it.key == placeId.id }

            val visited = getAllPlacesWithVisited(db, uid)
            visitedPlaces.value = visited
            isVisited.value = visited.any { it.key == placeId.id }

            val comm = getAllComment(db, placeId.id)
            commentsList.value = comm
        }
    }

    Column(modifier = Modifier.fillMaxSize()
        .verticalScroll(scrollState, true)
        .background(color = DrawerColor)
    ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = placeId.imageUrl,
                    contentDescription = "Фото места",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Row(modifier = Modifier.padding(10.dp)) {
                Text(
                    modifier = Modifier
                        .weight(1f)
                        .padding(top = 10.dp),
                    text = placeId.name,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                IconButton(
                    onClick = {
                        coroutineScope.launch {
                            val updatedFavorites = onFavoriteClick(
                                db,
                                isFavorite.value,
                                uid,
                                Favorites(placeId.id)
                            )
                            favPlaces.value = updatedFavorites
                            isFavorite.value = updatedFavorites.any { it.key == placeId.id }
                        }
                    }
                ) {
                    Icon(
                        painter = painterResource(
                            id = if (isFavorite.value) R.drawable.fav_true
                            else R.drawable.fav_false
                        ),
                        contentDescription = "fav",
                        tint = Color.White,
                        modifier = Modifier.size(32.dp)
                    )
                }

                IconButton(
                    onClick = {
                        coroutineScope.launch {
                            val updatedVisited = onVisitedClick(
                                db,
                                isVisited.value,
                                uid,
                                Visited(placeId.id)
                            )
                            visitedPlaces.value = updatedVisited
                            isVisited.value = updatedVisited.any { it.key == placeId.id }
                        }
                    }
                ) {
                    Icon(
                        painter = painterResource(
                            id = if (isVisited.value) R.drawable.done_true
                            else R.drawable.done_false
                        ),
                        contentDescription = "done",
                        tint = Color.White,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)

            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = getAddressFromLatLng(
                        context,
                        placeId.lat.toDouble(),
                        placeId.lng.toDouble()
                    ),
                    fontSize = 16.sp,
                    color = Color.White

                )
                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = placeId.description,
                    fontSize = 20.sp,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = "Комментарии",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(10.dp))

                Row{
                    Text(
                        modifier = Modifier
                            .weight(1f),
                        text = "Ваша оценка: ",
                        fontSize = 20.sp,
                        color = Color.White,
                        fontStyle = FontStyle.Italic

                    )
                    StarRatingBar(rating.value, { rating.value = it })
                }

                Spacer(modifier = Modifier.height(10.dp))
                RoundCornerTextField(
                    text = comment.value,
                    label = "Comment",
                    maxLines = 5
                ) {

                    comment.value = it
                }
                Spacer(modifier = Modifier.height(10.dp))
                LoginButton(text = "Отправить") {
                    coroutineScope.launch {
                        val newKey = firestore.collection("places")
                            .document(placeId.id)
                            .collection("comments")
                            .document()
                            .id

                        val newComment = CommentObject(
                            key = newKey,
                            placeId = placeId.id,
                            userId = uid,
                            userEmail = userEmail,
                            text = comment.value,
                            time = Timestamp.now(),
                            rating = rating.value
                        )

                        val updatedComments = onAddComment(firestore, placeId.id, newComment)
                        commentsList.value = updatedComments
                        comment.value = "" // очистка поля
                        rating.value = 0
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))
            }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            commentsList.value.forEach { comm ->
                CommentCard(
                    commentObject = comm,
                    db = db,
                    placeId = placeId.id,
                    commentsList = commentsList
                )
            }
        }
    }
}

fun getAddressFromLatLng(context: Context, lat: Double, lng: Double): String {
    val geocoder = Geocoder(context, Locale("ru"))
    return try {
        val addresses = geocoder.getFromLocation(lat, lng, 1)
        if (!addresses.isNullOrEmpty()) {
            val address = addresses[0]
            val city = address.locality
            val region = address.adminArea
            val fullAddress = address.getAddressLine(0)

            "Город: $city\nОбласть: $region\nАдрес: $fullAddress"
            return fullAddress
        } else {
            "Адрес не найден"
        }
    } catch (e: IOException) {
        e.printStackTrace()
        "Ошибка при получении адреса"
    }
}

suspend fun onFavoriteClick(
    db: FirebaseFirestore,
    isFavorite: Boolean,
    uid: String,
    favorite: Favorites
): List<Favorites> {

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


suspend fun getAllPlacesWithFavorites(
    db: FirebaseFirestore,
    userId: String
): List<Favorites> {
    return try {
        val snapshot = db.collection("users")
            .document(userId)
            .collection("favorites")
            .get()
            .await()

        snapshot.documents.mapNotNull { it.toObject(Favorites::class.java) }
    } catch (e: Exception) {
        emptyList()
    }
}

suspend fun getAllComment(
    db: FirebaseFirestore,
    placeId: String
): List<CommentObject> {
    return try {
        val snapshot = db.collection("places")
            .document(placeId)
            .collection("comments")
            .get()
            .await()

  //      snapshot.documents.mapNotNull { it.toObject(CommentObject::class.java) }
        snapshot.documents.mapNotNull { doc ->
            val comm = doc.toObject(CommentObject::class.java)
            comm?.apply { key = doc.id }
        }
    } catch (e: Exception) {
        emptyList()
    }
}

suspend fun onAddComment(
    db: FirebaseFirestore,
    placeId: String,
    //uid: String,
    comment: CommentObject
): List<CommentObject> {
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
    comment: CommentObject
): List<CommentObject> {
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

suspend fun onVisitedClick(
    db: FirebaseFirestore,
    isVisited: Boolean,
    uid: String,
    visited: Visited
): List<Visited> {
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
): List<Visited> {
    return try {
        val snapshot = db.collection("users")
            .document(userId)
            .collection("visited")
            .get()
            .await()

        snapshot.documents.mapNotNull { it.toObject(Visited::class.java) }
    } catch (e: Exception) {
        emptyList()
    }
}

