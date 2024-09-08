package ru.justlearn

object Dependencies {
    object Android {
        const val coreKtx = "androidx.core:core-ktx:1.12.0"
    }

    object Compose {
        private const val version = "1.8.2"

        const val activity = "androidx.activity:activity-compose:$version"
        const val ui = "androidx.compose.ui:ui"
        const val uiGraphics = "androidx.compose.ui:ui-graphics"
        const val toolingPreview = "androidx.compose.ui:ui-tooling-preview"
        const val material = "androidx.compose.material3:material3:1.2.1"
        const val uiTooling = "androidx.compose.ui:ui-tooling"
        const val testManifest = "androidx.compose.ui:ui-test-manifest"
    }

    object Lifecycle {
        const val lifecycleKtx = "androidx.lifecycle:lifecycle-runtime-ktx:2.7.0"
        const val composeViewModel = "androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0"
    }

    object Hilt {
        private const val version = "2.49"
        const val android = "com.google.dagger:hilt-android:$version"
        const val navigation = "androidx.hilt:hilt-navigation-compose:1.2.0"
        const val compiler = "com.google.dagger:hilt-compiler:$version"
    }

    object Navigation {
        const val navigationCompose = "androidx.navigation:navigation-fragment-compose:2.8.0"
    }

    object Serialization {
        const val serialization = "org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0"
    }
}