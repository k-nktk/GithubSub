package com.example.githubsub.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.example.application.SettingsPreference
import com.example.githubsub.data.SettingsSerializer
import com.example.githubsub.repository.datastore.DataStoreRepository
import com.example.githubsub.repository.datastore.IDataStoreRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object UserResultModule {
    @Provides
    @Singleton
    fun providesUserPreferencesDataStore(
        @ApplicationContext context: Context,
        resultSerializer: SettingsSerializer
    ): DataStore<SettingsPreference> =
        DataStoreFactory.create(
            serializer = resultSerializer,
        ) {
            context.dataStoreFile("result.pb")
        }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class UserResultRepositoryModule {
    @Singleton
    @Binds
    abstract fun bindUserResultRepository(
        impl: DataStoreRepository
    ): IDataStoreRepository
}
