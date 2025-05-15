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
        icon = R.drawable.all
    )
    object Monument : BottomMenuItem(
        route = "monument",
        title = "Памятники",
        icon = R.drawable.pamyatnik
    )
    object Attraction : BottomMenuItem(
        route = "attraction",
        title = "Достопримечательности",
        icon = R.drawable.dostop
    )
    object Nature : BottomMenuItem(
        route = "naturalBeaty",
        title = "Природа",
        icon = R.drawable.nature
    )
    object LocalCulture : BottomMenuItem(
        route = "localCulture",
        title = "Локальная культура",
        icon = R.drawable.lokal
    )
    object Museum : BottomMenuItem(
        route = "museum",
        title = "Музеи",
        icon = R.drawable.museum
    )
}