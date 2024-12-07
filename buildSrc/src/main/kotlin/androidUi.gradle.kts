plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-android")
    id("com.google.dagger.hilt.android")
    id("com.google.devtools.ksp")
    id("org.jetbrains.kotlin.plugin.compose")
}

android {
    compileSdk = App.compileSdk

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    defaultConfig {
        minSdk = App.minSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }
    compileOptions {
        sourceCompatibility = Version.sourceCompatibility
        targetCompatibility = Version.targetCompatibility
    }
    kotlinOptions {
        jvmTarget = Version.jvmTarget

        freeCompilerArgs += listOf(
            "-P",
            "plugin:org.jetbrains.kotlin.plugin.compose:stabilityConfigurationPath=" +
                    project.rootProject.projectDir.absolutePath + "/compose_compiler_config.conf",
        )
    }

    defaultConfig {
        vectorDrawables {
            useSupportLibrary = true
        }
    }
    buildFeatures {
        compose =  true
    }
    kotlin {
        jvmToolchain(Version.jvmVersion)
    }
}

internal val libs = rootProject.extensions.getByType<VersionCatalogsExtension>().named("libs")

dependencies {
    implementation(libs.findLibrary("androidx.core.ktx").get())
    implementation(libs.findLibrary("appcompat").get())
    implementation(libs.findLibrary("activity.compose").get())
    implementation(platform(libs.findLibrary("compose.bom").get()))
    implementation(libs.findLibrary("compose.ui").get())
    implementation(libs.findLibrary("composeUIToolingPreview").get())
    implementation(libs.findLibrary("composeUIGraphics").get())
    implementation(libs.findLibrary("material").get())
    implementation(libs.findLibrary("composeMaterial").get())
    implementation(libs.findLibrary("composeMaterial3").get())
    implementation(libs.findLibrary("coil").get())
    implementation(libs.findLibrary("viewModelCompose").get())
    implementation(libs.findLibrary("lifecycleCompose").get())
    implementation(libs.findLibrary("composeNavigation").get())
    implementation(libs.findLibrary("hiltComposeNavigation").get())

    debugImplementation(libs.findLibrary("composeUIToolingDebug").get())

    implementation(libs.findLibrary("hilt").get())
    ksp(libs.findLibrary("hilt.compiler").get())
}