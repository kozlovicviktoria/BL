package com.example.bl.placeDetailsScreen

import com.google.firebase.Timestamp


data class CommentObject (
    var placeId: String = "",
    var userId: String = "",
    val text: String = "",
    var time: Timestamp? = null
)