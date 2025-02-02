package com.example.bl.logScreen

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.bl.ui.theme.Blue4

@Composable
fun LoginButton(
    text: String,
    onClick: () -> Unit
) {
    Button(
        onClick = {
            onClick()
        },
        modifier = Modifier
            .width(130.dp)
            .height(50.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Blue4
        )
    ) {
        Text(text = text)
    }

}