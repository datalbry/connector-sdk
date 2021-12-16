plugins {
    id("io.datalbry.plugin.semver") version "0.2.2"
    id("datalbry.publish-maven-central")
    id("datalbry.kotlin")
    idea
}

subprojects {
    repositories {
        mavenCentral()
        mavenLocal()
    }
}

semver {
    version("alpha", "alpha.{COMMIT_TIMESTAMP}")
    version("beta", "beta.{COMMIT_TIMESTAMP}")
}

group = "io.datalbry.connector"

val values = tasks.create<Copy>("prepareDocs") {
    from("templates/docs")
    into("docs")
    expand(project.properties)
}
