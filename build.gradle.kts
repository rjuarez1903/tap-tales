// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.2.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
}

buildscript {
    repositories {

        mavenCentral()
        // Include any other repositories you need
    }
    dependencies {
        val ksp_version = "1.9.21-1.0.16" // Use the appropriate version
        classpath("com.google.devtools.ksp:symbol-processing-api:$ksp_version")
    }
}