package com.example.wonderbel.logScreen.viewModel

import com.example.wonderbel.navigation.data.MainScreenObject
import com.google.firebase.auth.FirebaseAuth

class LoginRepository {

    fun signUp(
        auth: FirebaseAuth,
        email: String,
        password: String,
        onSignUpSuccess: (MainScreenObject)-> Unit,
        onSignUpFailure: (String)-> Unit,
    ){
        if (email.isBlank() || password.isBlank()){
            onSignUpFailure("Email or password cannot be empty")
            return
        }
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener{ task ->
                if(task.isSuccessful) onSignUpSuccess(
                    MainScreenObject(
                        task.result.user?.uid!!,
                        task.result.user?.email!!
                    )
                )
            }
            .addOnFailureListener {
                onSignUpFailure(it.message ?: "Sign Up Error")
            }
    }

    fun signIn(
        auth: FirebaseAuth,
        email: String,
        password: String,
        onSignInSuccess: (MainScreenObject)-> Unit,
        onSignInFailure: (String)-> Unit
    ){
        if (email.isBlank() || password.isBlank()){
            onSignInFailure("Email or password cannot be empty")
            return
        }
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener{ task ->
                if(task.isSuccessful) onSignInSuccess(
                    MainScreenObject(
                        task.result.user?.uid!!,
                        task.result.user?.email!!
                    )
                )
            }
            .addOnFailureListener {
                onSignInFailure(it.message ?: "Sign In Error")
            }
    }
}