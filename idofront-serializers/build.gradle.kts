plugins {
    id("com.mineinabyss.conventions.kotlin")
    id("com.mineinabyss.conventions.papermc")
    id("com.mineinabyss.conventions.publication")
    kotlin("plugin.serialization")
}

dependencies {
    compileOnly(libs.kotlin.reflect)
    compileOnly(libs.kotlinx.serialization.json)
    implementation(project(":idofront-util"))
    implementation(project(":idofront-text-components"))
}
