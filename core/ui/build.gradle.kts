plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id(libs.plugins.compose.compiler.get().pluginId)
}

android {
    namespace = "${App.namespace}.core.ui"
    compileSdk = App.compileSdk

    defaultConfig {
        minSdk = App.minSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
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

    buildFeatures {
        compose = true
    }
    kotlin {
        jvmToolchain(Version.jvmVersion)
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(platform(libs.compose.bom))
    implementation(libs.activity.compose)
    implementation(libs.compose.ui)
    implementation(libs.composeUIGraphics)
    implementation(libs.composeUIToolingPreview)
    implementation(libs.composeMaterial)
    implementation(libs.composeMaterial3)
    implementation(libs.composeNavigation)
    implementation(libs.lifecycleCompose)
    implementation(libs.hiltComposeNavigation)
    implementation(libs.coil)
    implementation(libs.material)
    implementation(libs.accompanistPermissions)

    debugImplementation(libs.composeUIToolingDebug)
}