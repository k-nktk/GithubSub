package com.example.githubsub.model

import com.squareup.moshi.Json

data class UserList(
    val items: List<UserItem>
)

data class UserItem(
    val id: Int,
    @Json(name = "login") val login: String,
    @Json(name = "avatar_url") val imageUrl: String
)