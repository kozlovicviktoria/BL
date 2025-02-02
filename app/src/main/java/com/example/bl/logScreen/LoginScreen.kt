package com.example.bl.logScreen

import android.util.Log
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bl.R
import com.example.bl.ui.theme.BackColor
import com.example.bl.ui.theme.ErrorColor
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun LoginScreen(){

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
    val ff = remember { mutableStateOf(false) }

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
        Image(painter = painterResource(id = R.drawable.logo1),
            contentDescription = "LG",
            modifier = Modifier.size(100.dp)
            )
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
            label = "Password"
        ) {
            passwordState.value = it
        }

        Spacer(modifier = Modifier.height(10.dp))

//        if(errorState.value.isNotEmpty()){
//            Text(
//                text = errorState.value,
//                color = ErrorColor,
//                textAlign = TextAlign.Center,
//                fontStyle = FontStyle.Italic,
//                fontWeight = FontWeight.Bold,
//                fontSize = 16.sp
//            )
//        }


        if(errorState.value.isEmpty() && ff.value){
            MyBottomText("User is registered", Color.Blue)
        } else if (errorState.value.isNotEmpty()){
            MyBottomText(errorState.value, ErrorColor)
        }


//        if(errorState.value.isNotEmpty()){
//            MyBottomText(errorState.value, ErrorColor)
//        } else if (ff.value){
//        MyBottomText("User is registered", Color.Blue)
//        }

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
                    onSignInSuccess = {
                        Log.d("MyLog", "Sign In Success")
//                        val intent = Intent(this, SecondActivity::class.java)
//                        startActivity(intent)
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
                    onSignUpSuccess = {
                        Log.d("MyLog", "Sign Up Success")
                        ff.value = true

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
           onSignUpSuccess: ()-> Unit,
           onSignUpFailure: (String)-> Unit
){
    if (email.isBlank() || password.isBlank()){
        onSignUpFailure("Email or password cannot be empty")
        return
    }
    auth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener{ task ->
            if(task.isSuccessful) onSignUpSuccess()
        }
        .addOnFailureListener {
            onSignUpFailure(it.message ?: "Sign Up Error")
        }
}

fun signIn(
    auth: FirebaseAuth,
    email: String,
    password: String,
    onSignInSuccess: ()-> Unit,
    onSignInFailure: (String)-> Unit
){
    if (email.isBlank() || password.isBlank()){
        onSignInFailure("Email or password cannot be empty")
        return
    }
    auth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener{ task ->
            if(task.isSuccessful) onSignInSuccess()
            val user = auth.currentUser
        }
        .addOnFailureListener {
            onSignInFailure(it.message ?: "Sign In Error")
        }
}

fun signOut(
    auth: FirebaseAuth
){
    auth.signOut()
}