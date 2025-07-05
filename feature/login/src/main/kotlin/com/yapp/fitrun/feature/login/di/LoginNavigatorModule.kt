package com.yapp.fitrun.feature.login.di

import android.app.Activity
import android.content.Intent
import com.yapp.fitrun.core.common.LoginNavigator
import com.yapp.fitrun.core.common.extension.startActivityWithAnimation
import com.yapp.fitrun.feature.login.LoginActivity
import com.yapp.fitrun.feature.navigator.INavigator
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Qualifier
import javax.inject.Singleton

internal class LoginNavigatorImpl @Inject constructor() : INavigator {
    override fun navigateFrom(
        activity: Activity,
        withFinish: Boolean,
        intentBuilder: Intent.() -> Intent,
    ) {
        activity.startActivityWithAnimation<LoginActivity>(
            withFinish = withFinish,
            intentBuilder = intentBuilder,
        )
    }
}

@Module
@InstallIn(SingletonComponent::class)
internal abstract class LoginNavigatorModule {
    @LoginNavigator
    @Singleton
    @Binds
    abstract fun bindLoginNavigator(loginNavigatorImpl: LoginNavigatorImpl): INavigator
}
