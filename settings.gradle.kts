enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
enableFeaturePreview("STABLE_CONFIGURATION_CACHE")

rootProject.name = "ComposeBoilerplate"
include(":app")
include(":feature")
include(":feature:list")
include(":core")
include(":core:ui")
include(":feature:list_api")
include(":domain")
include(":data")
include(":feature:details")
include(":feature:details_api")
