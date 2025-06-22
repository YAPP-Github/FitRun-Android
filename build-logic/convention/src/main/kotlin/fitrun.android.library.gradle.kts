import com.yapp.fitrun.configureCoroutineAndroid
import com.yapp.fitrun.configureKotest
import com.yapp.fitrun.configureKotlinAndroid

plugins {
    id("com.android.library")
    id("fitrun.verify.detekt")
}

configureKotlinAndroid()
configureKotest()
configureCoroutineAndroid()