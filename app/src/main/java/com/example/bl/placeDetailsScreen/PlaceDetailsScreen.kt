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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import java.util.Locale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.bl.R
import com.example.bl.data.Favorites
import com.example.bl.data.PlaceDBEntity
import com.example.bl.logScreen.LoginButton
import com.example.bl.logScreen.RoundCornerTextField
import com.example.bl.mainScreen.DrawerBody
import com.example.bl.mainScreen.DrawerHeader
import com.example.bl.navigation.DetailsNavObject
import com.example.bl.topMenu.TopMenu
import com.example.bl.ui.theme.DrawerColor
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.io.IOException



@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PlaceDetailsScreen(placeId: DetailsNavObject, navController: NavController) {

    val comment = remember { mutableStateOf("") }
    val firestore = remember { Firebase.firestore }
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    val isFavorite = remember { mutableStateOf(false) }
    val db = Firebase.firestore
    val favPlaces = remember { mutableStateOf(emptyList<Favorites>()) }
    val listOfPlaces = remember { mutableStateOf(emptyList<String>()) }
    val uid = FirebaseAuth.getInstance().currentUser?.uid.orEmpty()

// Загружаем избранные при первом запуске
    LaunchedEffect(Unit) {
        if (uid.isNotBlank()) {
            getAllPlacesWithFavorites(db, uid) { favs ->
                favPlaces.value = favs
                isFavorite.value = favs.any { it.key == placeId.id }
            }
        }
    }


    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = false,
        modifier = Modifier.fillMaxWidth(),
        drawerContent = {
            Column(
                modifier = Modifier.fillMaxWidth(0.7f)
                    .fillMaxHeight()
            ) {
                DrawerHeader(drawerState)
                DrawerBody()
            }
        }
    ) {
        Scaffold(topBar = { TopMenu(drawerState) })
        {
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
                Row(modifier = Modifier.padding(10.dp)){
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
                            onFavoriteClick(
                                db = db,
                                isFavorite = isFavorite.value,
                                uid = uid,
                                favorite = Favorites(placeId.id),
                            )
                            { updatedFavorites ->
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
                    }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)

                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = getAddressFromLatLng(context, placeId.lat.toDouble(), placeId.lng.toDouble()),
                        fontSize = 16.sp,
                        color = Color.White

                        )
                    Spacer(modifier = Modifier.height(10.dp))

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
                    RoundCornerTextField(
                        text = comment.value,
                        label = "Comment",
                        maxLines = 5
                    ) {

                        comment.value = it
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    LoginButton(text = "Отправить") {

                        saveComment(
                            firestore,
                            comment = CommentObject(
                                text = comment.value,
                                time = Timestamp.now()
                            ),
                            onSaved ={

                            },
                            onError = {

                            }
                        )

                    }
                }
                }
            }

    }
}



private fun saveComment(
    firestore: FirebaseFirestore,
    comment: CommentObject,
    onSaved: () -> Unit,
    onError: () -> Unit
){

    val db_place = firestore.collection("place")
    val key = db_place.document().id
    val db_comm = db_place.document(key).collection("comments")

    comment.userId = FirebaseAuth.getInstance().currentUser?.uid.toString()
        //comment.time = FieldValue.serverTimestamp()
    comment.placeId = key

//    val comment = hashMapOf(
//        "userId" to FirebaseAuth.getInstance().currentUser?.uid,
//        "placeId" to key,
//        "text" to comment,
//        "time" to FieldValue.serverTimestamp()
//    )

    db_comm.add(comment)
        .addOnSuccessListener {
            Log.d("Firebase", "Комментарий добавлен")
        }
        .addOnFailureListener { e ->
            Log.e("Firebase", "Ошибка", e)
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


private fun onFavoriteClick(
    db: FirebaseFirestore,
    isFavorite: Boolean,
    uid: String,
    favorite: Favorites,
    onUpdated: (List<Favorites>) -> Unit
) {
    val docRef = db.collection("users")
        .document(uid)
        .collection("favorites")
        .document(favorite.key)

    if (!isFavorite) {
        docRef.set(favorite)
            .addOnSuccessListener {
                Log.d("FAVORITE", "Добавлено в избранное")
                getAllPlacesWithFavorites(db, uid, onUpdated)
            }
            .addOnFailureListener {
                Log.e("FAVORITE", "Ошибка добавления", it)
            }
    } else {
        docRef.delete()
            .addOnSuccessListener {
                Log.d("FAVORITE", "Удалено из избранного")
                getAllPlacesWithFavorites(db, uid, onUpdated)
            }
            .addOnFailureListener {
                Log.e("FAVORITE", "Ошибка удаления", it)
            }
    }
}


fun getAllPlacesWithFavorites(
    db: FirebaseFirestore,
    uid: String,
    onResult: (List<Favorites>) -> Unit
) {
    db.collection("users")
        .document(uid)
        .collection("favorites")
        .get()
        .addOnSuccessListener { snapshot ->
            val favs = snapshot.toObjects(Favorites::class.java)
            onResult(favs)
        }
        .addOnFailureListener {
            Log.e("FAVORITE", "Ошибка получения избранного", it)
        }
}
