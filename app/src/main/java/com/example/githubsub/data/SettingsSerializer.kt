package com.example.githubsub.data

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.example.application.Settings
import com.example.application.SettingsPreference
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject

class SettingsSerializer @Inject constructor() : Serializer<SettingsPreference> {
    override val defaultValue: SettingsPreference = SettingsPreference.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): SettingsPreference {
        try {
            return SettingsPreference.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(
        t: SettingsPreference,
        output: OutputStream
    ) = t.writeTo(output)
}

val Context.settingsDataStore: DataStore<SettingsPreference> by dataStore(
    fileName = "settings.pb",
    serializer = SettingsSerializer()
)