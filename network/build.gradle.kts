plugins {
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)
    kotlin("plugin.serialization") version "2.0.0"
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

val serialization: String by project
val ktorClient: String by project
val koinVersion: String by project

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serialization")

    // ktor_client
    implementation("io.ktor:ktor-client-core:$ktorClient")
    implementation("io.ktor:ktor-client-cio:$ktorClient")
    implementation("io.ktor:ktor-client-content-negotiation:$ktorClient")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorClient")

    // koin
    implementation(platform("io.insert-koin:koin-bom:$koinVersion"))
    implementation("io.insert-koin:koin-core:$koinVersion")
}