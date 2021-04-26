import org.jetbrains.kotlin.util.suffixIfNot

plugins {
    id("datalbry.publish-maven-central")
    idea
}

subprojects {
    repositories {
        mavenCentral()
        mavenLocal()
    }
}

group = "io.datalbry.connector"
version = findVersion("0.0.10")

fun findVersion(baseVersion: String): String {
    return if (project.hasProperty("snapshot")) {
        return baseVersion.suffixIfNot("-SNAPSHOT")
    } else baseVersion
}
