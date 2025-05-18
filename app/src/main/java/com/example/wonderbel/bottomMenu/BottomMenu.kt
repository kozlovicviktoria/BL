package com.example.wonderbel.bottomMenu

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wonderbel.ui.theme.BottomButtonFalseColor
import com.example.wonderbel.ui.theme.BottomButtonTrueColor


@Composable
fun BottomMenu(onCategoryClick : (String) -> Unit = {}) {
    val selectedItem = remember { mutableStateOf<BottomMenuItem>(BottomMenuItem.All) }

    val filters = listOf(
        BottomMenuItem.All,
        BottomMenuItem.Monument,
        BottomMenuItem.Attraction,
        BottomMenuItem.Nature,
        BottomMenuItem.LocalCulture,
        BottomMenuItem.Museum
    )

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 14.dp),
            contentPadding = PaddingValues(horizontal = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(filters){ filter ->
            FilterButton(
                text = filter.title,
                isSelected = selectedItem.value == filter,
                onClick = {
                    selectedItem.value = filter
                    onCategoryClick(filter.route) // Передаём выбранную категорию
                },
                icon = filter.icon
            )

        }
    }
}

@Composable
fun  FilterButton(
    text: String,
    isSelected: Boolean,
    icon: Int,
    onClick: () -> Unit
){
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = BottomButtonTrueColor,
            contentColor = Color.Black,
            disabledContainerColor = BottomButtonFalseColor,
            disabledContentColor = Color.White
        ),
        enabled = !isSelected,
        shape = RoundedCornerShape(20.dp)
    ) {
        Row {
            Icon(painter = painterResource(icon), contentDescription = null, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = text,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }
    }
}
