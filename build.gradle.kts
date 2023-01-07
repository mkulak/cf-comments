plugins {
    kotlin("js") version "1.7.21"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

kotlin {
    js(IR) {
        nodejs {
        }
        binaries.executable()
    }
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-js:1.6.4")
    implementation(npm("better-sqlite3", "8.0.0"))
    implementation(npm("hono", "2.5.6"))
}