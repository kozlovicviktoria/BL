package com.example.wonderbel.placeDetailsScreen.ui

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wonderbel.R
import com.example.wonderbel.data.CommentEntity
import com.example.wonderbel.placeDetailsScreen.viewModel.PlaceDetailsViewModel

@Composable
fun CommentCard(
    commentObject: CommentEntity,
    placeId: String,
    viewModel: PlaceDetailsViewModel
) {
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
                                text = viewModel.formatTimestamp(commentObject.time),
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
                                viewModel.deleteComment(placeId, commentObject)
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



