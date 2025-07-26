package com.yapp.fitrun.core.room.di

import android.content.Context
import androidx.room.Room
import com.yapp.fitrun.core.room.dao.LocationDao
import com.yapp.fitrun.core.room.database.RunningDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): RunningDatabase {
        return Room.databaseBuilder(
            context,
            RunningDatabase::class.java,
            "running_database",
        ).build()
    }

    @Provides
    fun provideLocationDao(database: RunningDatabase): LocationDao = database.locationDao()
}
