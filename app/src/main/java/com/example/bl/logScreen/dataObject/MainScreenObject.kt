package com.example.bl.logScreen.dataObject

import kotlinx.serialization.Serializable

@Serializable
data class MainScreenObject(
    val userId: String = "",
    val email: String = ""
)
