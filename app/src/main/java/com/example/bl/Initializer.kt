package com.example.bl

import android.app.Application
import com.example.bl.logScreen.LoginScreen
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth

private lateinit var auth: FirebaseAuth

class Initializer : Application(){
        override fun onCreate() {
            super.onCreate()

            FirebaseApp.initializeApp(this)

        }
    }
