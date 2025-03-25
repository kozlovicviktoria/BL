package com.example.bl.placeDetailsScreen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bl.R
import com.example.bl.logScreen.LoginButton
import com.example.bl.logScreen.RoundCornerTextField
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase



@Composable
fun PlaceScreen() {

    val comment = remember { mutableStateOf("") }
    val firestore = remember {Firebase.firestore }



    Column(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(R.drawable.fon),
            contentDescription = "",
            modifier = Modifier.height(300.dp)
                .fillMaxWidth()
                .background(Color.LightGray),
            contentScale = ContentScale.FillHeight
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            text = "text",
            fontSize = 20.sp,
        )
        Spacer(modifier = Modifier.height(20.dp))
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