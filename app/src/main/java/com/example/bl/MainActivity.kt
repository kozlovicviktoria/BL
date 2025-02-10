package com.example.bl

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val currentUser = auth.currentUser

        setContent {

                NavigationApp(currentUser)

        }
    }
}



















