import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

allprojects {
    repositories {
        google()
        mavenCentral()
        maven { setUrl("https://jitpack.io") }
    }
    /**
     * Command: ./gradlew assembleRelease -PenableComposeMetrics=true --rerun-tasks
     * */
    tasks.withType<KotlinCompile>().configureEach {

        compilerOptions {
            if (project.findProperty("enableComposeMetrics") == "true") {
                freeCompilerArgs.addAll(
                    listOf(
                        "-P",
                        "plugin:androidx.compose.compiler.plugins.kotlin:reportsDestination=" +
                                project.projectDir.absolutePath + "/build/compose_reports",
                    )
                )
                freeCompilerArgs.addAll(
                    listOf(
                        "-P",
                        "plugin:androidx.compose.compiler.plugins.kotlin:metricsDestination=" +
                                project.projectDir.absolutePath + "/build/compose_metrics",
                    )
                )
            }
        }
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.layout.buildDirectory)
}