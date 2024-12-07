plugins {
    id("androidUi")
}

android {
    namespace = "${App.namespace}.details"
}

dependencies {
    implementation(projects.core.ui)
    implementation(projects.domain)
    implementation(projects.feature.detailsApi)
}