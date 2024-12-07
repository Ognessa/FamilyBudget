plugins {
    id("com.android.library")
    kotlin("android")
    id("kotlinx-serialization")
    id("com.google.dagger.hilt.android")
    id(libs.plugins.ksp.get().pluginId)
}

android {
    namespace = "${App.namespace}.data"
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
    implementation(projects.domain)

    implementation(libs.kotlin.std.lib)
    implementation(libs.coroutines.core)

    implementation(libs.logging.interceptor)
    implementation(libs.retrofit)
    implementation(libs.serialization.json)
    implementation(libs.serialization.converter)

    implementation(libs.hilt)
    ksp(libs.hilt.compiler)

    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)
}