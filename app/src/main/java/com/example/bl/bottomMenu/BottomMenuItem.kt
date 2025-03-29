package com.example.bl.bottomMenu

import com.example.bl.R

sealed class BottomMenuItem(
    val route: String,
    val title: String,
    val icon: Int
) {
    object All : BottomMenuItem(
        route = "all",
        title = "Все",
        icon = R.drawable.ic_monument
    )
    object Monument : BottomMenuItem(
        route = "monument",
        title = "Памятники",
        icon = R.drawable.ic_monument
    )
    object Attraction : BottomMenuItem(
        route = "attraction",
        title = "Достопримечательности",
        icon = R.drawable.ic_monument
    )
    object Nature : BottomMenuItem(
        route = "nature",
        title = "Природа",
        icon = R.drawable.ic_monument
    )
    object LocalCulture : BottomMenuItem(
        route = "monument",
        title = "Локальная культура",
        icon = R.drawable.ic_monument
    )
    object Museum : BottomMenuItem(
        route = "monument",
        title = "Музеи",
        icon = R.drawable.ic_monument
    )
}