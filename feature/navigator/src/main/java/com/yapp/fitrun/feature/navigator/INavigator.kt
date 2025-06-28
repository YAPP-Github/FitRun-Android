package com.yapp.fitrun.feature.navigator

import android.app.Activity
import android.content.Intent

// https://speakerdeck.com/fornewid/android-modularization-recipe?slide=45
interface INavigator {
    fun navigateFrom(
        activity: Activity,
        withFinish: Boolean,
        intentBuilder: Intent.() -> Intent = { this },
    )
}
