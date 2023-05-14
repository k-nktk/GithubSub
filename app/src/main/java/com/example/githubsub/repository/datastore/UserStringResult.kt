package com.example.githubsub.repository.datastore

sealed interface UserStringResult {
    data class UserName(
        val text: String
    ): UserStringResult
}
