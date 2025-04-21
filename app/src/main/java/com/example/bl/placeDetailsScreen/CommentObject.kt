package com.example.bl.placeDetailsScreen

import com.google.firebase.Timestamp


data class CommentObject (
    var key: String = "",
    var placeId: String = "",
    var userId: String = "",
    var userEmail: String = "",
    val text: String = "",
    var time: Timestamp? = null,
    var rating: Int = 0
)