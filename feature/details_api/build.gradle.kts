plugins {
    id("androidApi")
}

android {
    namespace = "${App.namespace}.details_api"

    defaultConfig {
        minSdk = App.minSdk
    }
}

dependencies {
    implementation(libs.composeNavigation)
}