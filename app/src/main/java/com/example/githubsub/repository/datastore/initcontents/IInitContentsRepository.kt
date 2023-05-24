package com.example.githubsub.repository.datastore.initcontents

import com.example.application.InitContentsPreference

interface IInitContentsRepository {
    suspend fun writeInitContentsResult(user: String)

    suspend fun getInitContentsResult(): Result<InitContentsPreference>
}

// 結果を返すクラス
sealed class Result<out T> {
    // 成功した場合
    data class Success<out T>(val data: T) : Result<T>()

    // 失敗した場合
    data class Error(val exception: Exception) : Result<Nothing>()
}