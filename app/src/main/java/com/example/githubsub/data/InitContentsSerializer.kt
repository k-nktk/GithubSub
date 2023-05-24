package com.example.githubsub.data


import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.example.application.InitContentsPreference
import com.example.application.Settings
import com.example.application.SettingsPreference
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject

class InitContentsSerializer @Inject constructor() : Serializer<InitContentsPreference> {
    override val defaultValue: InitContentsPreference = InitContentsPreference.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): InitContentsPreference {
        try {
            return InitContentsPreference.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(
        t: InitContentsPreference,
        output: OutputStream
    ) = t.writeTo(output)
}

val Context.initContentsDataStore: DataStore<InitContentsPreference> by dataStore(
    fileName = "initcontents.pb",
    serializer = InitContentsSerializer()
)