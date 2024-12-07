plugins {
    id("androidApi")
}

android {
    namespace = "${App.namespace}.list_api"

    defaultConfig {
        minSdk = App.minSdk
    }
}

dependencies {
    implementation(libs.composeNavigation)
}