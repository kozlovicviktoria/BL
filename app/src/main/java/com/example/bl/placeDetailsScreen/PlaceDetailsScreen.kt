package com.example.bl.placeDetailsScreen

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
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
import com.example.bl.logScreen.LoginButton
import com.example.bl.logScreen.RoundCornerTextField
import com.example.bl.mainScreen.DrawerBody
import com.example.bl.mainScreen.DrawerHeader
import com.example.bl.navigation.DetailsNavObject
import com.example.bl.topMenu.TopMenu
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
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
//    LaunchedEffect(Unit) {
//        val address = getAddressFromLatLng(context, placeId.lat.toDouble(), placeId.lng.toDouble())
//        Log.d("Address", address)
//    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = false,
        modifier = Modifier.fillMaxWidth(),
        drawerContent = {
            Column(
                modifier = Modifier.fillMaxWidth(0.7f)
                    .fillMaxHeight()
            ) {
                DrawerHeader()
                DrawerBody()
            }
        }
    ) {
        Scaffold(topBar = { TopMenu(drawerState) })
        {

            Column(modifier = Modifier.fillMaxSize()
                .verticalScroll(scrollState, true)) {
                Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(350.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        AsyncImage(
//                            model = ImageRequest.Builder(LocalContext.current)
//                                .data(placeId.imageUrl)
//                                .crossfade(true)
//                                .build(),
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
                            //.fillMaxWidth()
                            .padding(top = 10.dp),
                        text = placeId.name,
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold
                    )
                    IconButton(
                        onClick = {},
                        modifier = Modifier
                            //.align(Alignment.End)
                            //.padding(10.dp)

                    ) {

                        Icon(painter = painterResource(R.drawable.fav_false), contentDescription = "fav")

                    }
                }


                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    text = getAddressFromLatLng(context, placeId.lat.toDouble(), placeId.lng.toDouble()),
                    fontSize = 16.sp
                )

                Text(
                    modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
                    text = placeId.description,
                    fontSize = 20.sp,
        )
                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    text = "Комментарии",
                    fontSize = 22.sp,
                )
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