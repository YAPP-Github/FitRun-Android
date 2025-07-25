package com.yapp.fitrun.core.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.yapp.fitrun.core.datastore.TokenDataSource
import com.yapp.fitrun.core.datastore.TokenDataSourceImpl
import com.yapp.fitrun.core.datastore.WorkThroughDataSource
import com.yapp.fitrun.core.datastore.WorkThroughDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private const val TOKEN_DATASTORE = "token_datastore"
private val Context.tokenDataStore: DataStore<Preferences> by preferencesDataStore(name = TOKEN_DATASTORE)

private const val WORK_THROUGH_DATASTORE = "work_through_datastore"
private val Context.workThroughDataStore: DataStore<Preferences> by preferencesDataStore(name = WORK_THROUGH_DATASTORE)

@Module
@InstallIn(SingletonComponent::class)
internal object DataStoreModule {

    @TokenDataStore
    @Singleton
    @Provides
    internal fun provideTokenDataStore(@ApplicationContext context: Context) = context.tokenDataStore

    @WorkThroughDataStore
    @Singleton
    @Provides
    internal fun provideWorkThroughDataStore(@ApplicationContext context: Context) = context.workThroughDataStore
}

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Singleton
    @Binds
    abstract fun bindTokenDataSource(tokenDataSourceImpl: TokenDataSourceImpl): TokenDataSource

    @Singleton
    @Binds
    abstract fun bindWorkThroughDataSource(workThroughDataSourceImpl: WorkThroughDataSourceImpl): WorkThroughDataSource
}
