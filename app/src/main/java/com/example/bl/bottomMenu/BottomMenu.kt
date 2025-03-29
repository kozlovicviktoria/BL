package com.example.bl.bottomMenu

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun BottomMenu() {
    val filters = listOf(
        BottomMenuItem.All,
        BottomMenuItem.Monument,
        BottomMenuItem.Attraction,
        BottomMenuItem.Nature,
        BottomMenuItem.LocalCulture,
        BottomMenuItem.Museum
    )
    val selectedItem = remember { mutableStateOf("Все") }

    LazyRow(
        modifier = Modifier.fillMaxWidth()
            .padding(top = 18.dp),
        contentPadding = PaddingValues(horizontal = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(filters){ filter ->
            FilterButton(
                text = filter.title,
                isSelected = selectedItem.value == filter.title,
                onClick = { selectedItem.value = filter.title }
            )
        }
    }
}

@Composable
fun  FilterButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
){
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
//            containerColor = if (isSelected) Color.Black else Color.Red,
//            disabledContainerColor = if(isSelected) Color.DarkGray else Color.Red,
            containerColor = Color.Red,
            disabledContainerColor = Color.DarkGray,
            disabledContentColor = Color.White
        ),
        enabled = !isSelected,
        shape = RoundedCornerShape(20.dp)
    ) {
        Text(text = text)
    }

}