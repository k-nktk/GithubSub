package com.example.githubsub.model

import com.squareup.moshi.Json

/// 検索結果レスポンス
data class SearchedRepository(
    val items: List<SearchRepositoryItem>
)

data class SearchRepositoryItem(
    val id: Int,
    @Json(name = "full_name") val fullName: String
)