import com.yapp.fitrun.configureKotlin
import com.yapp.fitrun.configureKotest


plugins {
    kotlin("jvm")
    id("fitrun.verify.detekt")
}

configureKotlin()
configureKotest()