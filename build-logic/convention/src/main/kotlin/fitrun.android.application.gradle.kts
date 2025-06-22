import com.yapp.fitrun.configureKotlinAndroid
import com.yapp.fitrun.configureHiltAndroid
import com.yapp.fitrun.configureKotestAndroid


plugins {
    id("com.android.application")
}

configureKotlinAndroid()
configureHiltAndroid()
configureKotestAndroid()