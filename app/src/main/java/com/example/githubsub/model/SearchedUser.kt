package com.example.githubsub.model

import com.squareup.moshi.Json

data class SearchedUser(
    val items: List<SearchUserItem>
)

data class SearchUserItem(
    val id: Int,
    @Json(name = "login") val name: String,
    @Json(name = "avatar_url") val imageUrl: String
)