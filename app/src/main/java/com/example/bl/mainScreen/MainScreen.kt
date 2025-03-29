package com.example.bl.mainScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.HorizontalAlignmentLine
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.bl.R
import com.example.bl.auth
import com.example.bl.bottomMenu.BottomMenu
import com.example.bl.logScreen.LoginButton
import com.example.bl.logScreen.dataObject.LoginScreenObject
import com.example.bl.logScreen.dataObject.MainScreenObject
import com.example.bl.ui.theme.Blue4
import com.google.firebase.auth.FirebaseAuth


//@Composable
//fun MainTitle(navController: NavController) {
//    Row(
//        modifier = Modifier
//            .background(Color.LightGray)
//            .fillMaxWidth()
//            .height(80.dp)
//
//    ) {
//        Text(
//            modifier = Modifier
//                .width(50.dp)
//                .wrapContentHeight(),
//            text = "Travel",
//            color = Color.DarkGray,
//            fontSize = 35.sp,
//            fontFamily = FontFamily.Serif,
//            textAlign = TextAlign.Center,
//            letterSpacing = 3.sp
//        )
//        LoginButton(text = "Sign Out") {
//            signOut(auth, navController)
//
//    }
//    }
//
//}
//@Composable
//fun Footer(navData: MainScreenObject){
//    Column (
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(200.dp)
//            .background(Color.DarkGray)
//    ){
//
//        Text("User is signed in: ${navData.email}")
//    }
//}
//
//@Composable
//fun MainScreen(navData: MainScreenObject, navController: NavController){
//    Column (
//        modifier = Modifier
//            .fillMaxSize(),
//        verticalArrangement = Arrangement.SpaceAround,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Row {
//            MainTitle(navController)
//        }
//        Row {
//            CustomMap()
//        }
//        Row {
//            Footer(navData)
//        }
//    }
//}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(navData: MainScreenObject, navController: NavController) {


//    Image(painter = painterResource(id = R.drawable.fon),
//        contentDescription = "FL",
//        modifier = Modifier.fillMaxSize(),
//        contentScale = ContentScale.Crop
//    )

//    Column(
//        modifier = Modifier.fillMaxSize(),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ) {
//        Row(
//            modifier = Modifier
//                //.background(Color.LightGray)
//                .fillMaxWidth()
//                .height(70.dp),
//            horizontalArrangement = Arrangement.Center,
//            verticalAlignment = Alignment.CenterVertically
//
//        ) {
//            Image(painter = painterResource(id = R.drawable.ikon555),
//                contentDescription = "IK",
//                modifier = Modifier.size(40.dp)
//            )
//            Text(
//                text = "BL",
//                color = Color.DarkGray,
//                fontSize = 30.sp,
//                fontFamily = FontFamily.Serif,
//                textAlign = TextAlign.Left,
//                letterSpacing = 3.sp
//            )
//
//
//            Spacer(modifier = Modifier.width(120.dp))
//
//            Button(
//                onClick = {
//                    signOut(auth, navController)
//                },
//                modifier = Modifier
//                    .width(120.dp)
//                    .height(40.dp),
//                colors = ButtonDefaults.buttonColors(Color.White)
//            ){
//                Text(
//                    text = "Sign Out",
//                    color = Color.Black,
//                    fontWeight = FontWeight.Bold
//                )
//            }
//        }
//
//
//        Box(modifier = Modifier
//            .fillMaxWidth().
//            weight(3f)){
//            CustomMap()
//        }

//    Box (
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(80.dp)
//    ){
//
//        Text("User is signed in: ${navData.email}")
//    }

   // }
    val drawerState = rememberDrawerState(DrawerValue.Open)
    ModalNavigationDrawer(
        drawerState=drawerState,
        modifier = Modifier.fillMaxWidth(),
        drawerContent = {
            Column(
                modifier = Modifier.fillMaxWidth(0.7f)
                    .fillMaxHeight()
            ) {

                DrawerHeader()
                DrawerBody()

            }
        }
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = { BottomMenu() },
            //topBar = { BottomMenu() }
        ) {
            CustomMap()
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