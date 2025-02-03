package com.example.bl.mainScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun MainTitle() {
    Row(
        modifier = Modifier
            .background(Color.LightGray)
            .fillMaxWidth()
            .height(80.dp)

    ) {
        Text(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentHeight(),
            text = "Travel",
            color = Color.DarkGray,
            fontSize = 35.sp,
            fontFamily = FontFamily.Serif,
            textAlign = TextAlign.Center,
            letterSpacing = 3.sp
        )
    }

}
@Composable
fun Footer(){
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(Color.DarkGray)
    ){

        Text("jfjfjfj")
    }
}

@Composable
fun MainScreen(){
    Column (
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row {
            MainTitle()
        }
        Row {
            CustomMap()
        }
        Row {
            Footer()
        }
    }
}