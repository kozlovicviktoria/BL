package com.example.bl.drawer

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.bl.R
import com.example.bl.navigation.data.LoginScreenObject
import com.example.bl.ui.theme.DrawerColor
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

@Composable
fun DrawerHeader(drawerState: DrawerState, navController: NavController, firebaseAuth: FirebaseAuth) {

    val scope = rememberCoroutineScope()

    Column(
        Modifier
            .fillMaxWidth()
            .height(140.dp)
            .background(color = DrawerColor),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            IconButton(
                onClick = {
                    scope.launch { drawerState.close() }
                },
                modifier = Modifier
                    .padding(start = 10.dp, end = 40.dp)
            ) {
                Icon(
                    Icons.Default.Menu,
                    contentDescription = "menu",
                    Modifier.size(34.dp),
                    tint = Color.White
                )
            }
            Image(
                modifier = Modifier.size(70.dp),
                painter = painterResource(id = R.drawable.ikon555),
                contentDescription = "",
                alignment = Alignment.Center
            )

            IconButton(
                onClick = {
                    signOut(firebaseAuth, navController)
                },
                modifier = Modifier
                    .padding(start = 40.dp, end = 10.dp)
            ) {
                Icon(
                    Icons.Default.ExitToApp,
                    contentDescription = "exit",
                    Modifier.size(34.dp),
                    tint = Color.White
                )
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "WonderBel",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic,
            fontSize = 25.sp
        )
    }
}

fun signOut(
    auth: FirebaseAuth,
    navController: NavController
){
    auth.signOut()
    navController.navigate(LoginScreenObject)
}