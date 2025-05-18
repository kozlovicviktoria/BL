package com.example.wonderbel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.wonderbel.navigation.NavigationApp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val currentUser = auth.currentUser
        setContent {
                NavigationApp(currentUser)
        }
    }
}



















