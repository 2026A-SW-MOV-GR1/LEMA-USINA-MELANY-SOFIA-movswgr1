plugins {
    kotlin("multiplatform") version "1.9.10"
    id("com.android.library")
}

repositories {
    google()
    mavenCentral()
}

kotlin {
    android()
    jvm("desktop")

    sourceSets {
        val commonMain by getting {
            dependencies {
                // Add common dependencies here if needed
            }
        }
        val androidMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlin:kotlin-stdlib")
            }
        }
        val desktopMain by getting
    }
}

android {
    namespace = "com.example.kmp"
    compileSdk = 36

    defaultConfig {
        minSdk = 24
    }
}

