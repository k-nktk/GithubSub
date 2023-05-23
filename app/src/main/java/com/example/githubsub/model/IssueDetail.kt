package com.example.githubsub.model

import com.squareup.moshi.Json

data class IssueDetail (
    val id: Int,
    @Json(name = "body") val body: String,
    @Json(name = "user") val user: UserItem
)