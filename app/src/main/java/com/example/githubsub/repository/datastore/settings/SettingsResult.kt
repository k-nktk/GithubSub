package com.example.githubsub.repository.datastore.settings

sealed interface SettingsResult {
    data class UserName(
        val text: String
    ): SettingsResult
}
