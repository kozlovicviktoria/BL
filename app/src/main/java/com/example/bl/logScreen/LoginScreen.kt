package com.example.bl.logScreen

import android.media.Image
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bl.R
import com.example.bl.logScreen.dataObject.MainScreenObject
import com.example.bl.ui.theme.BackColor
import com.example.bl.ui.theme.ErrorColor
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


@Composable
fun LoginScreen(
    onNavigationMainScreen: (MainScreenObject) -> Unit
){

    val auth = remember{
        Firebase.auth
    }

    val errorState = remember {
        mutableStateOf("")
    }

    val emailState = remember {
        mutableStateOf("")
    }

    val passwordState = remember {
        mutableStateOf("")
    }
    val successAuth = remember { mutableStateOf(false) }

    Image(painter = painterResource(id = R.drawable.fon_login),
        contentDescription = "FL",
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
    )

    Box(modifier = Modifier.fillMaxSize()
        .background(color = BackColor))

    Column(modifier = Modifier.fillMaxSize().padding(
        start = 50.dp, end = 50.dp
    ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
       Row(modifier = Modifier.fillMaxWidth(),
           horizontalArrangement = Arrangement.Center,
           verticalAlignment = Alignment.CenterVertically) {

           Image(painter = painterResource(id = R.drawable.ikon555),
               contentDescription = "LG",
               modifier = Modifier.size(80.dp)
           )
           Text(text = "BEL\nTRAVEL",
               fontWeight = FontWeight.Bold,
               fontSize = 26.sp
               )
       }

        Spacer(modifier = Modifier.height(20.dp))
        RoundCornerTextField(
            text = emailState.value,
            label = "Email"
        ) {
            emailState.value = it
        }
        Spacer(modifier = Modifier.height(10.dp))
        RoundCornerTextField(
            text = passwordState.value,
            label = "Password",
            visible = false
        ) {
            passwordState.value = it
        }

        Spacer(modifier = Modifier.height(10.dp))

        if(errorState.value.isEmpty() && successAuth.value){
            MyBottomText("User is registered", Color.Blue)
        } else if (errorState.value.isNotEmpty()){
            MyBottomText(errorState.value, ErrorColor)
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically) {

            LoginButton(text = "Sign In") {

                signIn(
                    auth,
                    emailState.value,
                    passwordState.value,
                    onSignInSuccess = {navData ->
                        onNavigationMainScreen(navData)
                    },
                    onSignInFailure = {
                            error ->
                        errorState.value = error
                    }
                )
            }
            Spacer(modifier = Modifier.width(10.dp))

            LoginButton(text = "Sign Up") {

                signUp(
                    auth,
                    emailState.value,
                    passwordState.value,
                    onSignUpSuccess = {navData ->
                        onNavigationMainScreen(navData)
                        successAuth.value = true
                    },
                    onSignUpFailure = {
                        error ->
                        errorState.value = error
                    }
                )
            }
        }
    }
}

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
            if(task.isSuccessful) onSignUpSuccess(MainScreenObject(
                task.result.user?.uid!!,
                task.result.user?.email!!
            ))
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
            if(task.isSuccessful) onSignInSuccess(MainScreenObject(
                task.result.user?.uid!!,
                task.result.user?.email!!
            ))
        }
        .addOnFailureListener {
            onSignInFailure(it.message ?: "Sign In Error")
        }
}

