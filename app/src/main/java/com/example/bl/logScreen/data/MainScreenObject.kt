package com.example.bl.logScreen.data

import kotlinx.serialization.Serializable

@Serializable
data class MainScreenObject(
    val userId: String = "",
    val email: String = ""
)
