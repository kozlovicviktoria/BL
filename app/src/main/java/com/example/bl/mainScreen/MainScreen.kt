package com.example.bl.mainScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
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
import androidx.navigation.NavController
import com.example.bl.auth
import com.example.bl.logScreen.LoginButton
import com.example.bl.logScreen.dataObject.LoginScreenObject
import com.example.bl.logScreen.dataObject.MainScreenObject
import com.google.firebase.auth.FirebaseAuth


@Composable
fun MainTitle(navController: NavController) {
    Row(
        modifier = Modifier
            .background(Color.LightGray)
            .fillMaxWidth()
            .height(80.dp)

    ) {
        Text(
            modifier = Modifier
                .width(50.dp)
                .wrapContentHeight(),
            text = "Travel",
            color = Color.DarkGray,
            fontSize = 35.sp,
            fontFamily = FontFamily.Serif,
            textAlign = TextAlign.Center,
            letterSpacing = 3.sp
        )
        LoginButton(text = "Sign Out") {
            signOut(auth, navController)

    }
    }

}
@Composable
fun Footer(navData: MainScreenObject){
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(Color.DarkGray)
    ){

        Text("User is signed in: ${navData.email}")
    }
}

@Composable
fun MainScreen(navData: MainScreenObject, navController: NavController){
    Column (
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row {
            MainTitle(navController)
        }
        Row {
            CustomMap()
        }
        Row {
            Footer(navData)
        }
    }
}

fun signOut(
    auth: FirebaseAuth,
    navController: NavController
){
    auth.signOut()
    navController.navigate(LoginScreenObject)
}