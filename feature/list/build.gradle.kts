plugins {
    id("androidUi")
}

android {
    namespace = "${App.namespace}.list"
}

dependencies {
    implementation(projects.core.ui)
    implementation(projects.domain)
    implementation(projects.feature.listApi)
    implementation(projects.feature.detailsApi)
}