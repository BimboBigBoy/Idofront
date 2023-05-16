import org.jetbrains.kotlin.util.suffixIfNot

val releaseVersion: String? = System.getenv("RELEASE_VERSION")

fun getNextVersion(): String {
    if (releaseVersion == null) return "$version"
    if (releaseVersion.endsWith("SNAPSHOT")) return "$version".suffixIfNot("-SNAPSHOT")

    val (majorTarget, minorTarget) = version.toString().split(".")
    try {
        val (major, minor, patch) = releaseVersion.removePrefix("v").split(".")
        if (majorTarget == major && minorTarget == minor) {
            return "$major.$minor.${patch.toInt() + 1}"
        }
    } catch (_: Exception) {
    }
    return "$majorTarget.$minorTarget.0"
}

version = getNextVersion()

subprojects {
    version = rootProject.version
}
