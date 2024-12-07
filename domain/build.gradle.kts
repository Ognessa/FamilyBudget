plugins {
    id("com.android.library")
    kotlin("android")
    id("kotlin-parcelize")
    id("com.google.dagger.hilt.android")
    id(libs.plugins.ksp.get().pluginId)
}

android {
    namespace = "${App.namespace}.domain"
    compileSdk = App.compileSdk

    defaultConfig {
        minSdk = App.minSdk
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = Version.sourceCompatibility
        targetCompatibility = Version.targetCompatibility
    }
    kotlinOptions {
        jvmTarget = Version.jvmTarget
    }
    kotlin {
        jvmToolchain(Version.jvmVersion)
    }
}

dependencies {
    implementation(libs.kotlin.std.lib)
    implementation(libs.coroutines.core)
    implementation(libs.serialization.json)

    implementation(libs.hilt)
    ksp(libs.hilt.compiler)
}