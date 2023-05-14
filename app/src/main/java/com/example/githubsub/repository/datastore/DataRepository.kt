package com.example.githubsub.repository.datastore

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import com.example.application.Settings
import com.example.application.SettingsPreference
import com.example.githubsub.data.settingsDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataStoreRepository @Inject constructor(
    private val userResultDataStore: DataStore<SettingsPreference>,
//    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher,
) : IDataStoreRepository {
    override suspend fun writeUserResult(user: String) {
        withContext(Dispatchers.IO) {
            try {
                userResultDataStore.updateData { currentResult ->
                    currentResult.toBuilder().clear().setUser(user)
                        .build()
                }
            } catch (exception: Exception) {
                Log.e("QRCodeResult", "Failed to update user preferences")
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