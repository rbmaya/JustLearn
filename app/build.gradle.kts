import ru.justlearn.Android
import ru.justlearn.Compose
import ru.justlearn.DaggerHilt
import ru.justlearn.Dependencies
import ru.justlearn.Lifecycle
import ru.justlearn.Navigation
import ru.justlearn.Network
import ru.justlearn.Room
import ru.justlearn.Serialization

plugins {
    id("com.android.application")
    id("dagger.hilt.android.plugin")
    id("com.google.devtools.ksp")
    kotlin("android")
    kotlin("plugin.serialization")
    kotlin("plugin.parcelize")
}

repositories {
    google()
    mavenCentral()
}

android {
    namespace = Config.packageName
    compileSdk = Config.compileSdk

    defaultConfig {
        applicationId = Config.packageName
        minSdk = Config.minSdk
        targetSdk = Config.targetSdk
        versionCode = Config.versionCode
        versionName = Config.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    Android()
    Compose()
    Lifecycle()
    Navigation()
    Serialization()
    DaggerHilt()
    Network()
    Room()
}