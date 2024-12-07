plugins {
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
}
kotlin {
    jvmToolchain(17)
}
dependencies {
    // Gradle
    implementation(gradleApi())
    implementation(libs.gradle)
    implementation(libs.gradle.kotlinPlugin)
    implementation(libs.gradleApi)
    implementation(libs.ksp.gradlePlugin)
    implementation(libs.gradle.composeCompiler)

    implementation(libs.gradle.kotlinSerialization)
    implementation(libs.gradle.navigationSafeArgsPlugin)
    implementation(libs.gradle.googleServices)
    implementation(libs.gradle.firebaseCrashlytics)

    // Hilt
    implementation(libs.javapoet)
    implementation(libs.gradle.hiltPlugin)
}