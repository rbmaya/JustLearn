pluginManagement {
    repositories {
        google()
        mavenCentral()
        mavenLocal()
        gradlePluginPortal()
    }

    resolutionStrategy {
        eachPlugin {
            if (requested.id.id.startsWith("com.android")) {
                useModule("com.android.tools.build:gradle:8.2.1")
            }
            if (requested.id.id.startsWith("org.jetbrains.kotlin")) {
                useVersion("1.9.0")
            }
            if (requested.id.id.startsWith("dagger.hilt.android")) {
                useModule("com.google.dagger:hilt-android-gradle-plugin:2.49")
            }
            if (requested.id.id.startsWith("plugin.serialization")) {
                useModule("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")
            }
            if (requested.id.id.startsWith("com.google.devtools.ksp")) {
                useVersion("1.9.10-1.0.13")
            }
        }
    }
}

rootProject.name = "My Application"
include(":app")
