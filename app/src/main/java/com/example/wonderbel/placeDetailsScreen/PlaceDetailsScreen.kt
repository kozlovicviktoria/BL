package com.example.wonderbel.placeDetailsScreen

import android.annotation.SuppressLint
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.wonderbel.R
import com.example.wonderbel.logScreen.ui.LoginButton
import com.example.wonderbel.logScreen.ui.RoundCornerTextField
import com.example.wonderbel.navigation.data.DetailsNavObject
import com.example.wonderbel.placeDetailsScreen.ui.CommentCard
import com.example.wonderbel.placeDetailsScreen.ui.StarRatingBar
import com.example.wonderbel.placeDetailsScreen.viewModel.PlaceDetailsViewModel
import com.example.wonderbel.ui.theme.DrawerColor


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PlaceDetailsScreen(placeId: DetailsNavObject,
                       userEmail: String,
                       placeViewModel: PlaceDetailsViewModel = viewModel()) {

    val context = LocalContext.current
    val scrollState = rememberScrollState()

    placeViewModel.loadInitialData(placeId.id)

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
                        placeViewModel.toggleFavorite(placeId.id)
                    }
                ) {
                    Icon(
                        painter = painterResource(
                            id = if (placeViewModel.isFavorite) R.drawable.fav_true
                            else R.drawable.fav_false
                        ),
                        contentDescription = "fav",
                        tint = Color.White,
                        modifier = Modifier.size(32.dp)
                    )
                }

                IconButton(
                    onClick = {
                        placeViewModel.toggleVisited(placeId.id)
                    }
                ) {
                    Icon(
                        painter = painterResource(
                            id = if (placeViewModel.isVisited) R.drawable.done_true
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
                    text = placeViewModel.getAddressFromLatLng(
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
                    StarRatingBar(placeViewModel.rating, { placeViewModel.rating = it })
                }

                Spacer(modifier = Modifier.height(10.dp))
                RoundCornerTextField(
                    text = placeViewModel.comment,
                    label = "Comment",
                    maxLines = 5
                ) {
                    placeViewModel.comment = it
                }
                Spacer(modifier = Modifier.height(10.dp))
                LoginButton(text = "Отправить") {
                    placeViewModel.submitComment(placeId.id, userEmail)
                }

                Spacer(modifier = Modifier.height(10.dp))
            }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            placeViewModel.commentsList.forEach { comm ->
                CommentCard(
                    commentObject = comm,
                    placeId = placeId.id,
                    placeViewModel
                )
            }
        }
    }
}

