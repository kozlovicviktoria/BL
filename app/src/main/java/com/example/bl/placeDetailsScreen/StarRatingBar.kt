package com.example.bl.placeDetailsScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.bl.R

@Composable
fun StarRatingBar(
    rating: Int,
    onRatingChanged: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        (1..5).forEach { star ->
            Icon(
                painter = if (star <= rating) {
                    painterResource(id = R.drawable.star)
                } else {
                    painterResource(id = R.drawable.baseline_star_outline_24)
                },
                contentDescription = " ",
                tint = Color.Yellow,
                modifier = Modifier
                    .size(28.dp)
                    .clickable { onRatingChanged(star) }
            )
        }
    }
}
