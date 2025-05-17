package com.example.bl.placeDetailsScreen

import android.icu.text.SimpleDateFormat
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bl.R
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import java.util.Locale

@Composable
fun CommentCard(
    commentObject: CommentObject,
    db:FirebaseFirestore,
    placeId: String,
    commentsList: MutableState<List<CommentObject>>
) {
    val coroutineScope = rememberCoroutineScope()


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .padding(8.dp),
        colors = CardDefaults.cardColors(Color.LightGray),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Box(
        ) {
            Row(
                modifier = Modifier.padding(8.dp),
            ) {

                Column(

                ) {

                    Row {
                        Column(
                            modifier = Modifier.weight(1f),
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(
                                text = commentObject.userEmail,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp
                            )
                            Text(
                                text = formatTimestamp(commentObject.time),
                                fontSize = 12.sp
                            )
                        }

                            repeat(commentObject.rating) {
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = null,
                                    tint = Color.Yellow,
                                    modifier = Modifier.size(20.dp)
                                )
                            }

                        IconButton(
                            onClick = {
                                coroutineScope.launch {
                                    val updatedComments =
                                        onDeleteComment(db, placeId, commentObject)
                                    commentsList.value = updatedComments
                                }
                            }
                        ){
                            Icon(
                                painter = painterResource(
                                    id = R.drawable.delete
                                ),
                                contentDescription = "delete"
                            )
                        }
                    }

                    Text(
                        text = commentObject.text,
                        fontSize = 16.sp
                    )
                }
                }
            }
        }
    }

fun formatTimestamp(timestamp: Timestamp?): String {
    if (timestamp == null) return "Неизвестно"

    val date = timestamp.toDate()
    val formatter = SimpleDateFormat("dd MMMM yyyy, HH:mm", Locale("ru"))
    return formatter.format(date)
}

