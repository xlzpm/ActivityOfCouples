plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.google.android.libraries.mapsplatform.secrets.gradle.plugin)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.example.app"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.app"
        minSdk = 30
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

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
        buildConfig = true
    }
    composeOptions {
        composeCompiler {
            enableStrongSkippingMode = true

            reportsDestination = layout.buildDirectory.dir("compose_compiler")
        }
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

val koinVersion: String by project
val decomposeVersion: String by project
val coilVersion: String by project

dependencies {
    implementation(project(":shared:components"))
    implementation(project(":firebase"))
    implementation(project(":firebase:auth"))
    implementation(project(":firebase:firestore"))
    implementation(project(":localdb"))
    implementation(project(":localdb:activities"))
    implementation(project(":mvi"))
    implementation(project(":network"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.generativeai)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // icons
    implementation (libs.androidx.material.icons.extended)

    // coil
    implementation("io.coil-kt:coil-compose:$coilVersion")

    // decompose
    implementation("com.arkivanov.decompose:decompose:$decomposeVersion")
    implementation("com.arkivanov.decompose:extensions-compose:$decomposeVersion")

    // koin
    implementation(platform("io.insert-koin:koin-bom:$koinVersion"))
    implementation("io.insert-koin:koin-core:$koinVersion")
    implementation(libs.koin.android)
}