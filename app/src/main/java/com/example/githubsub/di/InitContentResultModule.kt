package com.example.githubsub.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.example.application.InitContentsPreference
import com.example.application.SettingsPreference
import com.example.githubsub.data.InitContentsSerializer
import com.example.githubsub.data.SettingsSerializer
import com.example.githubsub.repository.datastore.initcontents.IInitContentsRepository
import com.example.githubsub.repository.datastore.initcontents.InitContentsRepository
import com.example.githubsub.repository.datastore.settings.ISettingsRepository
import com.example.githubsub.repository.datastore.settings.SettingsRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object InitContentResultModule {
    @Provides
    @Singleton
    fun providesInitContentsPreferencesDataStore(
        @ApplicationContext context: Context,
        resultSerializer: InitContentsSerializer
    ): DataStore<InitContentsPreference> =
        DataStoreFactory.create(
            serializer = resultSerializer,
        ) {
            context.dataStoreFile("result.pb")
        }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class InitContentResultRepositoryModule {
    @Singleton
    @Binds
    abstract fun bindInitContentResultRepository(
        impl: InitContentsRepository
    ): IInitContentsRepository
}
