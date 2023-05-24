package com.example.githubsub.repository.datastore.initcontents

sealed interface SettingsResult {
    data class UserName(
        val text: String
    ): SettingsResult
}
