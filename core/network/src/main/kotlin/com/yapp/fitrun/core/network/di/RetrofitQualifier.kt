package com.yapp.fitrun.core.network.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class TokenOkHttpClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BaseOkHttpClient