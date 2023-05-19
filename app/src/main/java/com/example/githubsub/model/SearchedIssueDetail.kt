package com.example.githubsub.model

import com.squareup.moshi.Json

data class SearchedIssueDetail (
    val id: Int,
    @Json(name = "body") val body: String,
    @Json(name = "user") val user: User
)