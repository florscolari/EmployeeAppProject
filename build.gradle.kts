// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
    }

    dependencies {
        //val navVersion = "2.7.5"
        classpath(libs.androidx.navigation.safe.args.gradle.plugin)
    }
}

// KPS instead of KAPT
plugins {
    id ("com.android.application") version "8.9.3" apply false
    id ("com.android.library") version "8.9.3" apply false
    id ("org.jetbrains.kotlin.android") version "2.1.21" apply false
    id("com.google.devtools.ksp") version "2.1.20-1.0.32" apply false
//id("com.google.devtools.ksp") version "1.9.0-1.0.13" apply false
}