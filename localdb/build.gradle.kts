plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.example.db_games"
    compileSdk = 34

    defaultConfig {
        minSdk = 30

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
}

val koinVersion: String by project
val roomVersion: String by project

dependencies {

    implementation(project(":localdb:activities"))
    implementation(project(":network"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //room
    implementation(libs.androidx.room.runtime)
    ksp("androidx.room:room-compiler:$roomVersion")
    implementation(libs.androidx.room.ktx)

    // koin
    implementation(platform("io.insert-koin:koin-bom:$koinVersion"))
    implementation("io.insert-koin:koin-core:$koinVersion")
    implementation(libs.koin.android)
}