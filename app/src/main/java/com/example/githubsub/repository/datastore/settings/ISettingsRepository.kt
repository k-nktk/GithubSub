package com.example.githubsub.repository.datastore.settings

import com.example.application.SettingsPreference

interface ISettingsRepository {
    suspend fun writeUserResult(user: String)

    suspend fun getUserResult(): Result<SettingsPreference>
}

// 結果を返すクラス
sealed class Result<out T> {
    // 成功した場合
    data class Success<out T>(val data: T) : Result<T>()

    // 失敗した場合
    data class Error(val exception: Exception) : Result<Nothing>()
}