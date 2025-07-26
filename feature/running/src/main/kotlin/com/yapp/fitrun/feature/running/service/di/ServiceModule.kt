package com.yapp.fitrun.feature.running.service.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

//    @Provides
//    @Singleton
//    fun provideFusedLocationProviderClient(
//        @ApplicationContext context: Context
//    ): FusedLocationProviderClient {
//        return LocationServices.getFusedLocationProviderClient(context)
//    }
}
