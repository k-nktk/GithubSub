package com.example.githubsub.repository.datastore.settings

import android.util.Log
import androidx.datastore.core.DataStore
import com.example.application.SettingsPreference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsRepository @Inject constructor(
    private val userResultDataStore: DataStore<SettingsPreference>,
) : ISettingsRepository {
    override suspend fun writeUserResult(user: String) {
        withContext(Dispatchers.IO) {
            try {
                userResultDataStore.updateData { currentResult ->
                    currentResult.toBuilder().clear().setUser(user)
                        .build()
                }
            } catch (exception: Exception) {
                Log.e("writeUserResult", "Failed to update user preferences")
            }
        }
    }

    override suspend fun getUserResult(): Result<SettingsPreference> =
        withContext(Dispatchers.IO) {
            try {
                Result.Success(data = userResultDataStore.data.first())
            } catch (exception: Exception) {
                Result.Error(exception = exception)
            }
        }
}