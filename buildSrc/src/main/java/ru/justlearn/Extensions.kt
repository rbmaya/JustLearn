package ru.justlearn

import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.implementation(dependency: String) {
    add("implementation", dependency)
}

fun DependencyHandler.implementation(dependency: Dependency) {
    add("implementation", dependency)
}

fun DependencyHandler.ksp(dependency: String) {
    add("ksp", dependency)
}

fun DependencyHandler.testImplementation(dependency: String) {
    add("testImplementation", dependency)
}

fun DependencyHandler.androidTestImplementation(dependency: String) {
    add("androidTestImplementation", dependency)
}

fun DependencyHandler.androidTestImplementation(dependency: Dependency) {
    add("androidTestImplementation", dependency)
}

fun DependencyHandler.debugImplementation(dependency: String) {
    add("debugImplementation", dependency)
}

fun DependencyHandler.Android() {
    implementation(Dependencies.Android.coreKtx)
}

fun DependencyHandler.Compose() {
    implementation(Dependencies.Compose.activity)
    implementation(Dependencies.Compose.ui)
    implementation(Dependencies.Compose.uiGraphics)
    implementation(Dependencies.Compose.toolingPreview)
    implementation(Dependencies.Compose.material3)
    implementation(Dependencies.Compose.material)

    debugImplementation(Dependencies.Compose.uiTooling)
    debugImplementation(Dependencies.Compose.testManifest)
}

fun DependencyHandler.Lifecycle() {
    implementation(Dependencies.Lifecycle.lifecycleKtx)
    implementation(Dependencies.Lifecycle.composeViewModel)
}

fun DependencyHandler.Navigation() {
    implementation(Dependencies.Navigation.navigationCompose)
}

fun DependencyHandler.Serialization() {
    implementation(Dependencies.Serialization.serialization)
}

fun DependencyHandler.DaggerHilt() {
    implementation(Dependencies.Hilt.android)
    implementation(Dependencies.Hilt.navigation)
    ksp(Dependencies.Hilt.compiler)
}

fun DependencyHandler.Network() {
    implementation(Dependencies.Network.retrofit)
    implementation(Dependencies.Network.gsonConverterFactory)
    implementation(Dependencies.Network.gson)
    implementation(Dependencies.Network.loggingInterceptor)
}

fun DependencyHandler.Room() {
    implementation(Dependencies.Room.room)
    ksp(Dependencies.Room.compiler)
}

