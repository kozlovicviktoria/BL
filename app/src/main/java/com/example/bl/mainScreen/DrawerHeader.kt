package com.example.bl.mainScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bl.R
import com.example.bl.topMenu.TopMenu
import com.example.bl.ui.theme.DrawerColor
import com.example.bl.ui.theme.TopColor
import kotlinx.coroutines.launch

@Composable
fun DrawerHeader(drawerState: DrawerState) {

    val scope = rememberCoroutineScope()

    Column(
        Modifier
            .fillMaxWidth()
            .height(140.dp)
            .background(color = DrawerColor),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
    ) {
            IconButton(
                onClick = {
                    scope.launch { drawerState.close() }
                },
                modifier = Modifier
                    .padding(start = 10.dp)
            ){
                Icon(Icons.Default.Menu, contentDescription = "menu", Modifier.size(34.dp), tint = Color.White)
            }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
               // .padding(top = 10.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier.size(70.dp),
                   // .weight(1f),
                painter = painterResource(id = R.drawable.ikon555),
                contentDescription = "",
                //alignment = Alignment.Center
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "WonderBel",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic,
                fontSize = 25.sp
                //textAlign = TextAlign.Center
            )
        }
        }
}