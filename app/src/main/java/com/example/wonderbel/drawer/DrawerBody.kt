package com.example.wonderbel.drawer

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wonderbel.R
import com.example.wonderbel.navigation.data.FavNavObject
import com.example.wonderbel.navigation.data.MainScreenObject
import com.example.wonderbel.navigation.data.VisitedNavObject

@Composable
fun DrawerBody(
    onNavigationFavScreen: (FavNavObject) -> Unit,
    onNavigationMapScreen: (MainScreenObject) -> Unit,
    onNavigationVisitedScreen: (VisitedNavObject) -> Unit,
    userId: String,
    email: String
) {
    val categoriesList = listOf(
        "Карта",
        "Избранное",
        "Посещенные"
    )

    Box(
        modifier = Modifier.fillMaxSize()
            .background(color = Color.DarkGray)
    ){

        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(R.drawable.fon_login),
            contentDescription = "",
            alpha = 0.3f,
            contentScale = ContentScale.Crop
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(categoriesList) { item ->
                Column(
                    Modifier.fillMaxWidth()
                        .clickable {
                            when(
                                item
                            ) {
                                "Карта" -> onNavigationMapScreen(MainScreenObject(userId, email))
                                "Избранное" -> onNavigationFavScreen(FavNavObject(userId))
                                "Посещенные" -> onNavigationVisitedScreen(VisitedNavObject(userId))
                            }
                        }
                ) {
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = item,
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(),
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(Color.LightGray)
                    )
                }
            }
        }
    }
}