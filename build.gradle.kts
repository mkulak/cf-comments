plugins {
    kotlin("js") version "1.9.21"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

kotlin {
    js(IR) {
        useEsModules()
        nodejs {
        }
        binaries.executable()
    }
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-js:1.7.3")
    implementation(npm("better-sqlite3", "8.0.0"))
    implementation(npm("hono", "2.5.6"))
}

tasks.register("buildProd") {
    doLast {
        val inputFile = file("build/js/packages/cf-comments/kotlin/cf-comments.mjs")
        val outputFile = file("build/js/packages/cf-comments/kotlin/worker.js")
        val content = inputFile.readText()

        val modifiedContent = content
            .replace("export {", "export default {")
            .replace("fetch as fetch", "fetch: fetch")

        outputFile.writeText(modifiedContent)
    }
}
tasks.getByName("buildProd").dependsOn(tasks.getByName("compileProductionExecutableKotlinJs"))