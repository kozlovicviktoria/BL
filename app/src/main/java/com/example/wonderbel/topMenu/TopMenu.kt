package com.example.wonderbel.topMenu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.wonderbel.ui.theme.TopColor
import kotlinx.coroutines.launch

@Composable
fun TopMenu(
   drawerState: DrawerState
) {
    val scope = rememberCoroutineScope()

    Row (
        modifier = Modifier.fillMaxWidth()
            .height(40.dp)
            .background(TopColor)
    ){
        IconButton(
            onClick = {
                scope.launch { drawerState.open() }
            },
            modifier = Modifier
                .padding(start = 10.dp)
            ){
            Icon(Icons.Default.Menu, contentDescription = "menu", Modifier.size(34.dp), tint = Color.White)
        }
    }
}