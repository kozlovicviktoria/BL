package com.example.bl.logScreen

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bl.R
import com.example.bl.logScreen.ui.LoginButton
import com.example.bl.logScreen.ui.LoginBottomText
import com.example.bl.logScreen.ui.RoundCornerTextField
import com.example.bl.logScreen.viewModel.LoginViewModel
import com.example.bl.navigation.data.MainScreenObject
import com.example.bl.ui.theme.BackColor
import com.example.bl.ui.theme.ErrorColor


@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel = viewModel(),
    onNavigationMainScreen: (MainScreenObject) -> Unit
){

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
            text = loginViewModel.emailState,
            label = "Email"
        ) {
            loginViewModel.emailState = it
        }
        Spacer(modifier = Modifier.height(10.dp))
        RoundCornerTextField(
            text = loginViewModel.passwordState,
            label = "Password",
            visible = false
        ) {
            loginViewModel.passwordState = it
        }

        Spacer(modifier = Modifier.height(10.dp))

        if(loginViewModel.errorState.isEmpty() && loginViewModel.successAuth){
            LoginBottomText("User is registered", Color.Blue)
        } else if (loginViewModel.errorState.isNotEmpty()){
            LoginBottomText(loginViewModel.errorState, ErrorColor)
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically) {

            LoginButton(text = "Sign In") {
                loginViewModel.signInUser(onNavigationMainScreen)
            }
            Spacer(modifier = Modifier.width(10.dp))

            LoginButton(text = "Sign Up") {
                loginViewModel.signUpUser(onNavigationMainScreen)
            }
            }
        }
    }



