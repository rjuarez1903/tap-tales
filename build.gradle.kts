// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.2.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.21" apply false
    id("com.google.dagger.hilt.android") version "2.46.1" apply false
}

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        val kspVersion = "1.9.21-1.0.16"
        classpath("com.google.devtools.ksp:symbol-processing-api:$kspVersion")
    }
}