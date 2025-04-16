package com.example.bl.navigation

import kotlinx.serialization.Serializable

@Serializable
data class VisitedNavObject(
    val userId: String = ""
)
