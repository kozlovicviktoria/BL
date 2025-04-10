package com.example.bl.navigation

import kotlinx.serialization.Serializable

@Serializable
data class MainScreenObject(
    val userId: String = "",
    val email: String = ""
)
