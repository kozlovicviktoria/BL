package com.example.wonderbel.logScreen.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.wonderbel.navigation.data.MainScreenObject
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginViewModel(
    private val repository: LoginRepository,
):ViewModel() {
    var auth = Firebase.auth
    var errorState by mutableStateOf("")
    var emailState by mutableStateOf("")
    var passwordState by mutableStateOf("")
    var successAuth by mutableStateOf(false)


    fun signUpUser(onNavigationMainScreen: (MainScreenObject) -> Unit) {
        repository.signUp(auth, emailState, passwordState,
            onSignUpSuccess = { navData ->
                onNavigationMainScreen(navData)
                successAuth = true
            },
            onSignUpFailure = { error ->
                errorState = error
            }
        )
    }

    fun signInUser(onNavigationMainScreen: (MainScreenObject) -> Unit) {
        repository.signIn(auth, emailState, passwordState,
            onSignInSuccess = { navData ->
                onNavigationMainScreen(navData)
                successAuth = true
            },
            onSignInFailure = { error ->
                errorState = error
            }
        )
    }
}
