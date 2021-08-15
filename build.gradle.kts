import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("com.mineinabyss.conventions.kotlin")
    id("com.mineinabyss.conventions.papermc")
    id("com.mineinabyss.conventions.publication")
    kotlin("plugin.serialization")
}

dependencies {
    compileOnly("org.jetbrains.kotlinx:kotlinx-serialization-json")
    compileOnly("com.charleskorn.kaml:kaml")
    api(project("idofront-slimjar"))
}

allprojects {
    repositories {
        maven("https://repo.vshnv.tech/releases/")
    }

    tasks {
        withType<KotlinCompile> {
            kotlinOptions {
                freeCompilerArgs = listOf(
                    "-Xopt-in=kotlin.RequiresOptIn",
                )
            }
        }
    }
}
