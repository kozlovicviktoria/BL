package com.example.bl.navigation.data

import kotlinx.serialization.Serializable

@Serializable
data class MainScreenObject(
    val userId: String = "",
    val email: String = ""
)
