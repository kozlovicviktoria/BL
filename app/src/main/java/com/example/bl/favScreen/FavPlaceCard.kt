package com.example.bl.favScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.bl.data.PlaceDBEntity
import com.example.bl.navigation.DetailsNavObject

@Composable
fun FavPlaceCard(
    place: PlaceDBEntity,
    //onClick: () -> Unit,
    onNavigationDetailsScreen: (DetailsNavObject) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                //onClick()
                onNavigationDetailsScreen(DetailsNavObject(
                    place.id,
                    place.name,
                    place.description,
                    place.imageUrl,
                    place.point.latitude.toString(),
                    place.point.longitude.toString(),
                    place.isFavorite
                ))
                       },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = place.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = place.description.take(100) + "...",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
