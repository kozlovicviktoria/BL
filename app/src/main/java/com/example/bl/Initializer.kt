package com.example.bl

import android.app.Application
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

lateinit var auth: FirebaseAuth
class Initializer : Application(){
        override fun onCreate() {
            super.onCreate()
            FirebaseApp.initializeApp(this)
            auth = Firebase.auth
        }
    }


