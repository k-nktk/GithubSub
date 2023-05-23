package com.example.githubsub.model

import com.squareup.moshi.Json

/// 検索結果レスポンス
data class ProjectList(
    val items: List<ProjectItem>
)

data class ProjectItem(
    val id: Int,
    @Json(name = "full_name") val fullName: String,
    @Json(name = "name") val name:String
)