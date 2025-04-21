package com.example.bl.placeDetailsScreen

import android.icu.text.SimpleDateFormat
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.bl.R
import com.example.bl.data.Favorites
import com.example.bl.data.PlaceDBEntity
import com.example.bl.data.Visited
import com.example.bl.navigation.DetailsNavObject
import com.example.bl.placeDetailsScreen.onFavoriteClick
import com.example.bl.placeDetailsScreen.onVisitedClick
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import org.w3c.dom.Comment
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
            .padding(8.dp)
            .clickable {
//                onNavigationDetailsScreen(
//                    DetailsNavObject(
//                        place.id,
//                        place.name,
//                        place.description,
//                        place.imageUrl,
//                        place.point.latitude.toString(),
//                        place.point.longitude.toString(),
//                        place.isFavorite
//                    )
//                )
            },
        colors = CardDefaults.cardColors(Color.LightGray),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Box(
        ) {
            Row(
                modifier = Modifier.padding(8.dp),
            ) {

                Column(
                    //modifier = Modifier.padding(2.dp)
//                        .weight(1f)

                ) {

                    Row {
                        Column(
                            modifier = Modifier.weight(1f),
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(
//                                modifier = Modifier
//                                    //.weight(1f)
//                                    .padding(5.dp),
                                text = commentObject.userEmail,
                                //style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp
                            )
                            //Spacer(Modifier.height(2.dp))
                            Text(
                                text = formatTimestamp(commentObject.time),
                                //style = MaterialTheme.typography.titleMedium,
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
                    //Spacer(Modifier.height(2.dp))

                    Text(
                        text = commentObject.text,
                        //style = MaterialTheme.typography.bodySmall,
                        fontSize = 16.sp
                    )
                }

//                IconButton(
//                    modifier = Modifier
//                        .padding(8.dp),
//                    onClick = {
//
//                        // viewModel.toggleFavorite(uid, placeId.id)
//                        coroutineScope.launch {
//                            val updatedFavs =
//                                onFavoriteClick(db, isFavorite.value, uid, Favorites(place.id))
//                            favPlaces.value = updatedFavs
//                            isFavorite.value = updatedFavs.any { it.key == place.id }
//                        }
////                    onFavoriteClick(
////                        db = db,
////                        isFavorite = isFavorite.value,
////                        uid = uid,
////                        favorite = Favorites(place.id),
////                    )
////                    { updatedFavorites ->
////                        favPlaces.value = updatedFavorites
////                        isFavorite.value = updatedFavorites.any { it.key == place.id }
////                    }
//                    }
//                ) {
//                    Icon(
//                        painter = painterResource(
//                            id = if (isFavorite.value) R.drawable.fav_true
//                            else R.drawable.fav_false
//                        ),
//                        contentDescription = "fav",
//                        tint = Color.White,
//                        modifier = Modifier.size(32.dp)
//                    )
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

