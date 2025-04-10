package com.example.bl.mainScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bl.R
import org.w3c.dom.Text as Text

@Composable
fun DrawerBody() {
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
//        Column(
//            modifier = Modifier.fillMaxSize(),
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//
//        }
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(categoriesList) { item ->
                Column(
                    Modifier.fillMaxWidth()
                        .clickable {}
                ) {
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = item,
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(),
                        //.height(70.dp),
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
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