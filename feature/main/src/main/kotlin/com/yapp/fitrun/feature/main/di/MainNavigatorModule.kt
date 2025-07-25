package com.yapp.fitrun.feature.main.di

import android.app.Activity
import android.content.Intent
import com.yapp.fitrun.core.common.MainNavigator
import com.yapp.fitrun.core.common.extension.startActivityWithAnimation
import com.yapp.fitrun.feature.navigator.INavigator
import com.yapp.fitrun.feature.main.MainActivity
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

internal class MainNavigatorImpl @Inject constructor() : INavigator {
    override fun navigateFrom(
        activity: Activity,
        withFinish: Boolean,
        intentBuilder: Intent.() -> Intent,
    ) {
        activity.startActivityWithAnimation<MainActivity>(
            withFinish = withFinish,
            intentBuilder = intentBuilder,
        )
    }
}

@Module
@InstallIn(SingletonComponent::class)
internal abstract class MainNavigatorModule {
    @MainNavigator
    @Singleton
    @Binds
    abstract fun bindMainNavigator(mainNavigatorImpl: MainNavigatorImpl): INavigator
}
