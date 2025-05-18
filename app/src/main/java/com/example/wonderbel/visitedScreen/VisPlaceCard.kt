package com.example.wonderbel.visitedScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.wonderbel.R
import com.example.wonderbel.data.PlaceDBEntity
import com.example.wonderbel.navigation.data.DetailsNavObject
import com.example.wonderbel.visitedScreen.viewModel.VisitedViewModel

@Composable
fun VisPlaceCard(
    place: PlaceDBEntity,
    uid: String,
    viewModel: VisitedViewModel,
    onNavigationDetailsScreen: (DetailsNavObject) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(8.dp)
            .clickable {
                onNavigationDetailsScreen(
                    DetailsNavObject(
                        place.id,
                        place.name,
                        place.description,
                        place.imageUrl,
                        place.point.latitude.toString(),
                        place.point.longitude.toString(),
                        place.isFavorite
                    )
                )
            },
        colors = CardDefaults.cardColors(Color.LightGray),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Box(
        ) {
            Row(
                modifier = Modifier.padding(8.dp),
            ) {
                AsyncImage(
                    model = place.imageUrl,
                    contentDescription = "Фото места",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(80.dp)
                )

                Column(
                    modifier = Modifier.padding(10.dp)
                        .weight(1f)

                ) {
                    Text(
                        text = place.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                    Text(
                        text = place.description.take(100) + "...",
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                IconButton(
                    modifier = Modifier
                        .padding(8.dp),
                    onClick = {
                        viewModel.updateVis(uid, place)
                    }
                ) {
                    Icon(
                        painter = painterResource(
                            id = if (viewModel.isVisited) R.drawable.done_true
                            else R.drawable.done_false
                        ),
                        contentDescription = "fav",
                        tint = Color.White,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
        }
    }
}

