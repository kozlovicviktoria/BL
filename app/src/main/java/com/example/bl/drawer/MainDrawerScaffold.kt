package com.example.bl.drawer

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.bl.topMenu.TopMenu
import com.google.firebase.auth.FirebaseAuth

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainDrawerScaffold(
    userId: String,
    email: String,
    navController: NavController,
    content: @Composable (DrawerState) -> Unit
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val firebaseAuth = FirebaseAuth.getInstance()

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = false,
        modifier = Modifier.fillMaxSize(),
        drawerContent = {
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .fillMaxHeight()
            ) {
                DrawerHeader(drawerState, navController, firebaseAuth)
                DrawerBody(
                    onNavigationFavScreen = { navController.navigate(it) },
                    onNavigationMapScreen = { navController.navigate(it) },
                    onNavigationVisitedScreen = { navController.navigate(it) },
                    userId = userId,
                    email = email
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopMenu(drawerState)
            }
        ) {
            content(drawerState)
        }
    }
}

