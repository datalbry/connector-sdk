import org.jetbrains.kotlin.util.suffixIfNot

val projectVersion: String by project
version = findVersion(projectVersion)

fun findVersion(baseVersion: String): String {
    return if (project.hasProperty("snapshot")) {
        return baseVersion.suffixIfNot("-SNAPSHOT")
    } else baseVersion
}

tasks.register<WriteProperties>("incrementVersion") {
    val currentMinor = projectVersion.substringAfterLast(".").toInt()
    val newVersion = "${projectVersion.substringBeforeLast(".")}.${currentMinor + 1}"
    this.outputFile = project.rootProject.file("gradle.properties")
    this.property("projectVersion", newVersion)
}
